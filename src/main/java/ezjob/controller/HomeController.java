package ezjob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ezjob.model.EmployerRegister;
import ezjob.model.Job;
import ezjob.model.User;
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
		model.addAttribute("companys", employerService.getTop9CompanyName());
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
	
}
