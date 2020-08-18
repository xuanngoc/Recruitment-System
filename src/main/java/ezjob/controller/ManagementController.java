package ezjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ezjob.model.SkillTag;
import ezjob.service.EmployerRegisterService;
import ezjob.service.EmployerService;
import ezjob.service.SkillTagService;
import ezjob.service.UserDetailServiceImp;

@Controller
@RequestMapping("/management/")
public class ManagementController {

	private SkillTagService skillTagService;
	private EmployerRegisterService employerRegisterService;
	private UserDetailServiceImp userDetailServiceImp;
	private EmployerService employerService;
	
	
	@Autowired
	public void setSkillTagService(SkillTagService skillTagService) {
		this.skillTagService = skillTagService;
	}
	
	@Autowired
	public void setEmployerRegisterService(EmployerRegisterService employerRegisterService) {
		this.employerRegisterService = employerRegisterService;
	}
	
	@Autowired
	public void setUserDetailServiceImp(UserDetailServiceImp userDetailServiceImp) {
		this.userDetailServiceImp = userDetailServiceImp;
	}
	
	@Autowired
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}

	@GetMapping()
	public String management() {
		return "redirect:employer-register-request";
	}
	
	@GetMapping("skill-tag")
	public String skillTagManagement(Model model) {
		model.addAttribute("skillTags", skillTagService.getListSkillTags());
		model.addAttribute("skillTag", new SkillTag());
		return "management/skill-tag/skill-tag";
	}
	
	@PostMapping("skill-tag")
	public String addKillTag(SkillTag skillTag) {
		skillTagService.saveOrUpdate(skillTag);
		return "redirect:skill-tag";
	}
	
	@GetMapping("skill-tag/{id}/edit")
	public String editSkillTag(@PathVariable long id, Model model) {
		model.addAttribute("skill", skillTagService.getSkillTagById(id));
		return "management/skill-tag/edit-skill-tag";
	}
	
	@PostMapping("skill-tag/{id}/update")
	public String updateSkillTag(@PathVariable long id, SkillTag skillTag) {
		skillTagService.saveOrUpdate(skillTag);
		return "redirect:/management/skill-tag";
	}
	
	@PostMapping("skill-tag/{id}/delete")
	public String deleteSkillTag(@PathVariable long id, SkillTag skillTag) {
		skillTagService.delete(id);
		return "redirect:/management/skill-tag";
	}
	
	@GetMapping("employer-register-request")
	public String employerRegisterRequest(Model model) {
		model.addAttribute("employerRegisterRequests", employerRegisterService.getAllEmployerRegistersPending());
		return "management/employer-register-request";
	}
	
	/*
	 * When manager accept request, create a User with username is email registed and random password,
	 * then send them via email. The employer will login and update their information
	 * */
	
	@PostMapping("employer-register-request/{id}")
	public String acceptEmployerRegisterRequestPending(@PathVariable long id) {
		employerRegisterService.acceptRequestPending(id);
		userDetailServiceImp.createEmployerUser(employerRegisterService.getEmployerRegisterById(id));
		return "redirect:/management/employer-register-request";
	}
	
	@GetMapping("employer")
	public String employerManagement(Model model) {
		model.addAttribute("employers", employerService.getAllEmployers());
		return "management/employer-management";
	}
	
}
