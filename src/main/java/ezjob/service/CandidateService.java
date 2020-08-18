package ezjob.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import ezjob.model.Candidate;
import ezjob.repository.CandidateRepository;
import ezjob.repository.UserRepository;

@Service
public class CandidateService {
	private CandidateRepository candidateRepository;
	private UserRepository userRepository;
	

	
	@Autowired
	public void setCanidateRepository(CandidateRepository canidateRepository) {
		this.candidateRepository = canidateRepository;
	}
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	 public Candidate getCandidateByUserName(String username) {
		long id = userRepository.findByUsername(username).getUserId();
		return candidateRepository.findByUserId(id);
	 }
		 
				
	public Candidate getCandidateById(long id) {
		return candidateRepository.findById(id).get();
		
	}
		public void saveOrUpdate(Candidate candidate) {
			
				
			candidate.setPath_file_cv(candidate.getPath_file_cv());
			candidate.setFullname(candidate.getFullname());
			candidateRepository.save(candidate);
			
		}
		
				 
	 
		 
		 
		 
}


