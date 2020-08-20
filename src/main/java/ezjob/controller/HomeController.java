package ezjob.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ezjob.model.EmployerRegister;
import ezjob.model.Job;
import ezjob.model.User;
import ezjob.model.dto.CompanyDTO;
import ezjob.model.dto.IComapnyDTO;
import ezjob.service.EmployerRegisterService;
import ezjob.service.EmployerService;
import ezjob.service.JobService;
import ezjob.service.UserDetailServiceImp;


@Controller
public class HomeController {
	
	private EmployerRegisterService employerRegisterService;
	private UserDetailServiceImp userDetailService;
	private EmployerService employerService;
	private JobService jobService;
	
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
