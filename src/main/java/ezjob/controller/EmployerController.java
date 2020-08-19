package ezjob.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ezjob.model.Employer;
import ezjob.model.Job;
import ezjob.service.EmployerService;
import ezjob.service.JobService;
import ezjob.service.SkillTagService;

@Controller
@RequestMapping("/employer/")
public class EmployerController {
	
	private EmployerService employerService;
	private JobService jobService;
	private SkillTagService skillTagService;
	
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
	public String updateInfo(Employer employer) {
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
	
 }
