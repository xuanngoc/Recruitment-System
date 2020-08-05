package ezjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ezjob.model.SkillTag;
import ezjob.service.SkillTagService;

@Controller
@RequestMapping("/management/")
public class ManagementController {

	@Autowired
	private SkillTagService skillTagService;
	
	@GetMapping
	public String management() {
		return "management/management";
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
	
	
	
}