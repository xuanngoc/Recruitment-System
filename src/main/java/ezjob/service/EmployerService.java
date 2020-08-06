package ezjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.Employer;
import ezjob.repository.EmployerRepository;

@Service
public class EmployerService {

	private EmployerRepository employerRepository;

	@Autowired
	public void setEmployerRepository(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
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
	
	
}
