package ezjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String admin() {
		return "home";
	}
	
	@GetMapping("employer-register")
	public String employerRegister() {
		return "employer-register";
	}
	
	
	@RequestMapping("/employer")
	public String admin3() {
		return "employer";
	}
	
}
