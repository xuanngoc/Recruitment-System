package ezjob.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.Employer;
import ezjob.repository.EmployerRepository;
import ezjob.repository.UserRepository;

@Service
public class EmployerService {

	private EmployerRepository employerRepository;
	private UserRepository userRepository;

	@Autowired
	public void setEmployerRepository(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Employer getEmployerById(long id) throws Exception {
		Employer employer = employerRepository.findById(id).get();
		if (employer != null) {
			return employer;
		}
		throw new Exception("Employer not found");
	}
	
	public void saveOrUpdate(Employer employer) {
		employerRepository.save(employer);
	}
	
	public Employer getEmployerByUsername(String username) {
		long userId = userRepository.findByUsername(username).getUserId();
		return employerRepository.findByUserId(userId);
	}
	
	public long getEmployerIdByUsername(String username) {
		return getEmployerByUsername(username).getEmployerId();
	}
}
