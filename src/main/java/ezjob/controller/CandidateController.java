package ezjob.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ezjob.model.Candidate;
import ezjob.service.CandidateService;


@Controller
@RequestMapping("/candidate/")
public class CandidateController {
	
	private final String UPLOAD_DIR = "Assets\\";

	private CandidateService candidateService;
	
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@GetMapping(path={"", "candidate-info"})
	public String candidateInfo( Authentication authentication,Model model) {
		String name = authentication.getName();
	    Candidate candidate = candidateService.getCandidateByUserName(name);
		model.addAttribute("candidate", candidate); 
		String fileName = candidate.getPath_file_cv();
		if(fileName != null) {
			model.addAttribute("filename", fileName.substring(7));
		}
		return "candidate/candidate-info";
	}
	
	@PostMapping(path= "candidate-info" )
	public String uploadFile( @RequestParam("path_file_cv") MultipartFile file, 
			RedirectAttributes attributes,
			@RequestParam("fullname") String fullname,
			@RequestParam("candidateId") long id ) {
		
        if (file.isEmpty()) {
           attributes.addFlashAttribute("message", "Please select a file to upload");
            return  "redirect:candidate-info";
        }
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

       attributes.addFlashAttribute("message", "Successfully uploaded " + fileName + '!');                      
       Candidate candidate = candidateService.getCandidateById(id);
       candidate.setFullname(fullname);
       candidate.setPath_file_cv(Paths.get(UPLOAD_DIR + fileName).toString());
       candidateService.saveOrUpdate(candidate);      
       return "redirect:candidate-info";
	} 
	
	@GetMapping(value = "candidate-info/view")
	public void showPDF(@RequestParam("path_file_cv") String file, HttpServletResponse response) throws IOException {
	    response.setContentType("application/pdf");
	    InputStream inputStream = new FileInputStream(new File(file));
	    int nRead;
	    while ((nRead = inputStream.read()) != -1) {
	        response.getWriter().write(nRead);
	    }
	    inputStream.close();
	}
	   
}