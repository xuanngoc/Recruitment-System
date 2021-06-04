package ezjob.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.joran.conditional.ElseAction;
import ezjob.model.ApplyingCV;
import ezjob.model.Employer;
import ezjob.model.Job;
import ezjob.service.ApplyingCVService;
import ezjob.service.EmployerService;
import ezjob.service.JobService;
import ezjob.service.SkillTagService;

@Controller
@RequestMapping("/employer/")
public class EmployerController {
	
	private static final String UPLOAD_DIR = "Assets\\image\\";
	
	@Value("${ezjob.pageSize}")
	private int pageSize  = 10; // default value if ezjob.pageSize in application.properties not exists
	
	private EmployerService employerService;
	private JobService jobService;
	private SkillTagService skillTagService;
	private ApplyingCVService applyingCVService;
	
	private InputStream in;
	
	@Autowired
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}
	
	@Autowired
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
	@Autowired
	public void setSkillTagService(SkillTagService skillTagService) {
		this.skillTagService = skillTagService;
	}
	
	@Autowired
	public void setApplyingCVService(ApplyingCVService applyingCVService) {
		this.applyingCVService = applyingCVService;
	}
	
	@GetMapping("") 
	public String redirectJobPage() {
		return "redirect:/employer/job";
	}
 	
	@GetMapping(path = {"info"})
	public String info(Authentication authentication, Model model) {
		Employer employer = employerService.getEmployerByUsername(authentication.getName());
		model.addAttribute("employer", employer);
		return "employer/update-info";
	}
	
	@PostMapping("info")
	public String updateInfo(@RequestParam(name = "path_file_image" , required = false) MultipartFile file, Employer employer) throws IOException {
		if (file != null) {
			String fileName = UPLOAD_DIR + employer.getEmployerId() + ".png";
			//in.close();
	        try {
	            Path path = Paths.get(fileName);
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            employer.setPathFileImage(fileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		employerService.saveOrUpdate(employer);
		return "redirect:info";
	}
	
	/*
	 * `@AuthenticationPrincipal User user` as a parameter in method instead of use
	 * Authentication (User) authentication.getPrincipal()
	 * You can also use 'java.security.Principal' to getName() then query name in User table to get User,
	 * OR you can getAuthentication in method like variable 'Authentication authentication =
 			SecurityContextHolder.getContext().getAuthentication();'
	 */
	
	@GetMapping(path = "job")
	public String jobManage(Authentication authentication,
			Model model,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String q) {
		if (page == null) {
			page = 1;
		}
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		long employerId = employerService.getEmployerIdByUsername(authentication.getName());
		Page<Job> pages;
		if (q == null) {
			pages = jobService.getJobsByEmployerId(employerId, pageable);
		} else {
			pages = jobService.getJobByTitleContainning(employerId, q, pageable);
		}
		
		model.addAttribute("jobs", pages);
		int totalPages = pages.getTotalPages();
		if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		//model.addAttribute("pageNumbers", totalPages);
		return "employer/job/job";
	}
	
	
	@GetMapping("job/{id}")
	public String detailJob(@PathVariable long id, Model model) {
		model.addAttribute("skillTags", skillTagService.getListSkillTags());
		Job job = jobService.getJobById(id);
		model.addAttribute("job", job);
		return "employer/job/detail-job";
	}
	
	@GetMapping("add-job")
	public String addJob(Model model) {
		model.addAttribute("job", new Job());
		model.addAttribute("skillTags", skillTagService.getListSkillTags());
		return "employer/job/add-job";
	}
	
	@PostMapping("job")
	public String saveJob(Authentication authentication, Job job) {
		Employer employer = employerService.getEmployerByUsername(authentication.getName());
		job.setEmployer(employer);
		jobService.saveOrUpdate(job);
		return "redirect:job";
	}
	
	@PostMapping("update-job")
	public String updateJob(Authentication authentication, Job job) {
		Employer employer = employerService.getEmployerByUsername(authentication.getName());
		job.setEmployer(employer);
		Date postedTime = jobService.getPostedTimeByJobId(job.getJobId());
		job.setPostedTime(postedTime);
		jobService.saveOrUpdate(job);
		return "redirect:job";
	}
	
	@PostMapping("job/{id}/stop-recruit")
	public String stopRecruit(@PathVariable long id) {
		Job job = jobService.getJobById(id);
		job.setClosed(true);
		jobService.saveOrUpdate(job);
		
		return "redirect:/employer/job";
	}
	
	@GetMapping("job/{id}/candidates")
	public String listCanditatesByJobId(@PathVariable long id,
			Model model,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String q) {
		
		if (page == null) {
			page = 1;
		}
		
		Page<ApplyingCV> pages;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		if (q == null) {
			pages = applyingCVService.getApplyingCVsByJobId(id, pageable);
		} else {
			pages = applyingCVService.getApplyingCVByJobIdAndTitleContainning(id, q, pageable);
		}
		model.addAttribute("jobId", id);
		model.addAttribute("listCVs", pages);
		int totalPages = pages.getTotalPages();
		if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		
		return "employer/job/list-cv-applyed";
	}
	
	@GetMapping(
			  value = "/{id}/image",
			  produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	)
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable long id) throws IOException, InterruptedException {
		try {
			in = new FileInputStream("Assets\\image\\" + id + ".png");
		} catch(FileNotFoundException excpt) {  
            Logger.getGlobal().log(Level.WARNING, "\nUsing default image");
            in = new FileInputStream("Assets\\image\\default.png");
        }		
	    return IOUtils.toByteArray(in);
	}
	
 }
