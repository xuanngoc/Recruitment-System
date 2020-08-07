package ezjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ezjob.model.Employer;
import ezjob.service.EmployerService;

@Controller
@RequestMapping("/employer/")
public class EmployerController {
	
	private EmployerService employerService;
	
	@Autowired
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}
	
	@RequestMapping(path = {"", "update-info"})
	public String info(Authentication authentication, Model model) {
		model.addAttribute("employer", employerService.getEmployerByUsername(authentication.getName()));
		return "employer/update-info";
	}
	
	@PostMapping("update-info")
	public String updateInfo(Employer employer) {
		employerService.saveOrUpdate(employer);
		return "redirect:update-info";
	}
	
	
	
	
}
