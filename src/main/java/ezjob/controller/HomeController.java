package ezjob.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseBody;

import ezjob.model.ApplyingCV;
import ezjob.model.Candidate;
import ezjob.model.EmployerRegister;
import ezjob.model.Job;
import ezjob.model.User;
import ezjob.service.ApplyingCVService;
import ezjob.service.CandidateService;
import ezjob.service.EmployerRegisterService;
import ezjob.service.EmployerService;
import ezjob.service.JobService;
import ezjob.service.UserDetailServiceImp;


@Controller
public class HomeController {
	
	private final String UPLOAD_DIR = "Assets\\cv\\";
	
	private EmployerRegisterService employerRegisterService;
	private UserDetailServiceImp userDetailService;
	private EmployerService employerService;
	private JobService jobService;
	private CandidateService candidateService;
	private ApplyingCVService applyingService;
	
	@Autowired
	public void setApplyingService(ApplyingCVService applyingService) {
		this.applyingService = applyingService;
	}
	
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@Autowired
	public void setUserDetailService(UserDetailServiceImp userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@Autowired
	public void setEmployerRegisterService(EmployerRegisterService employerRegisterService) {
		this.employerRegisterService = employerRegisterService;
	}
	
	@Autowired
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}
	
	@Autowired
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		List<Object> companys = employerService.getTop9CompanyName();
		//System.out.println(companys.get(0));
		model.addAttribute("companys", companys);
		return "home";
	}
	
	@GetMapping("/login")
    public String login() {
        return "login";
    }
		
	@GetMapping("employer-register")
	public String employerRegister(Model model) {
		model.addAttribute("employerRegister", new EmployerRegister());
		return "employer-register";
	}
	
	@PostMapping(path = "employer-register")
	public String sendEmployerRegister(EmployerRegister employerRegister) {
		employerRegisterService.saveOrUpdate(employerRegister);
		return "redirect:/";
	}
	
	@GetMapping("user-register")
	public String userRegister(Model model) {
		model.addAttribute("userRegister", new User());
		return "user-register";
	}
	
	@PostMapping(path = "user-register")
	public String saveUserRegister(User user) {
		userDetailService.createCandidateUser(user);
		return "redirect:/login";
	}
	
	@GetMapping(value = "/", params = {"city", "searchText"})
	public String searchJob(@RequestParam(required = false) String city,
			@RequestParam(required = false) String searchText,
			Model model) {
		
		
		List<Job> jobs = jobService.searchByCityAndDescription(city, searchText);
		model.addAttribute("searchText", searchText);
		model.addAttribute("jobs", jobs);
			
		return "home";
	}
	
	@GetMapping("job/{id}")
	public String showDetailJob(Model model,@PathVariable long id) {
		model.addAttribute("job", jobService.getJobById(id));
		return "detail-job";
	}
	
	@GetMapping(path= "job/{id}/apply" )
	public String info( @PathVariable long id,Model model,Authentication authentication)  {
		model.addAttribute("title", jobService.getJobById(id).getTitle());
		if(authentication!=null) {
			String name = authentication.getName();
		    Candidate candidate = candidateService.getCandidateByUserName(name);
			model.addAttribute("fullname", candidate.getFullname()); 
			model.addAttribute("path_file_cv", candidate.getPath_file_cv().toString()); 
			String fileName = candidate.getPath_file_cv();
			if(fileName != null) {
				model.addAttribute("filename", fileName.substring(7));
			}
		}
			
		 return "apply";
	}
	
	@PostMapping(path = "job/{id}/apply")
	public String sendCV(@PathVariable long id,Authentication authentication,
			@RequestParam("path_file_cv") MultipartFile file ,Model model) {
	
		ApplyingCV applyingCV = new ApplyingCV();
		if(authentication == null) {
			if (file.isEmpty()) {
				model.addAttribute("message",  "Please select a file ");
			    return  "redirect:job/{id}/apply";
			}
			String fileName = UUID.randomUUID().toString()+".pdf";
			try {
				Path path = Paths.get(UPLOAD_DIR + fileName);
			    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			    applyingCV.setPath_file_cv(path.toString());        
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute("message",  "Successfully uploaded " + fileName + '!');   
		
		}
		else {
			String name = authentication.getName();
			Candidate candidate = candidateService.getCandidateByUserName(name);
			applyingCV.setPath_file_cv(candidate.getPath_file_cv().toString());
			
		}
		
	    Job job=jobService.getJobById(id);
		applyingCV.setJob(job);
	    long millis = System.currentTimeMillis();  
		java.sql.Date date = new java.sql.Date(millis);  
		applyingCV.setDatetime(date);
		applyingService.saveOrUpdate(applyingCV);
		return "home";
	}
	
	@GetMapping("job/{id}/sendCVsuccess")
	public String success() {
		return "sendCVsuccess";	
	}
	
	@GetMapping(
			  value = "image/{id}",
			  produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	)
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable long id) throws IOException, InterruptedException {
		InputStream in;
		try {
			in = new FileInputStream("Assets\\image\\" + id + ".png");
		} catch(FileNotFoundException excpt) {  
          Logger.getGlobal().log(Level.WARNING, "\nUsing default image");
          in = new FileInputStream("Assets\\image\\default.png");
		}		
	    return IOUtils.toByteArray(in);
	}
	
}
	