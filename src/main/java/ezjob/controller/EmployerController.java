package ezjob.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Id;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ezjob.model.Employer;
import ezjob.model.Job;
import ezjob.service.EmployerService;
import ezjob.service.JobService;
import ezjob.service.SkillTagService;

@Controller
@RequestMapping("/employer/")
public class EmployerController {
	
	private static final String UPLOAD_DIR = "Assets\\image\\";
	private EmployerService employerService;
	private JobService jobService;
	private SkillTagService skillTagService;
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
	
	@GetMapping(path = {"info"})
	public String info(Authentication authentication, Model model) {
		Employer employer = employerService.getEmployerByUsername(authentication.getName());
		model.addAttribute("employer", employer);
		return "employer/update-info";
	}
	
	@PostMapping("/info")
	public String updateInfo(@RequestParam(name = "path_file_image" , required = false) MultipartFile file, Employer employer) throws IOException {
		if (file != null) {
			System.out.println(file);
			String fileName = UPLOAD_DIR + employer.getEmployerId() + ".png";
			in.close();
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
	
	@GetMapping(path = {"" ,"job"})
	public String jobManage(Authentication authentication, Model model) {
		long employerId = employerService.getEmployerIdByUsername(authentication.getName());
		model.addAttribute("jobs", jobService.getJobsByEmployerId(employerId));
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
