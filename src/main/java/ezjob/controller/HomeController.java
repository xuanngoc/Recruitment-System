package ezjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ezjob.model.Candidate;
import ezjob.model.EmployerRegister;
import ezjob.model.User;
import ezjob.service.CandidateService;
import ezjob.service.EmployerRegisterService;
import ezjob.service.UserDetailServiceImp;
import ezjob.service.UserRegisterService;

@Controller
public class HomeController {
	
	private EmployerRegisterService employerRegisterService;
	private UserRegisterService userRegisterService;
	private CandidateService candidateService;
	private UserDetailServiceImp userDetailService;
	
	@Autowired
	public void setUserDetailService(UserDetailServiceImp userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@Autowired
	public void setEmployerRegisterService(EmployerRegisterService employerRegisterService) {
		this.employerRegisterService = employerRegisterService;
	}
	
	@Autowired
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
	
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	
	@RequestMapping("/")
	public String admin() {
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
		
		return "redirect:/";
	}
	

	
	

	
	

	
}
