package ezjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/")
	public String admin() {
		return "admin";
	}
	
	@RequestMapping("/management")
	public String admin1() {
		return "admin";
	}
	
	@RequestMapping("/employer")
	public String admin3() {
		return "admin";
	}
	
}
