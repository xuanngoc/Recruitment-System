package ezjob.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.Job;
import ezjob.repository.JobRepository;

@Service
public class JobService {
	
	private JobRepository jobRepository;

	@Autowired
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	public Job getJobById(long id) throws EntityNotFoundException {
		Job job = jobRepository.findById(id).get();
		if (job != null) {
			return job;
		}
		throw new EntityNotFoundException("Job with " + id + " not found");
	}
	
	
	
	
	public List<Job> getJobsByEmployerId(long employerId) {
		return jobRepository.findJobByEmployerEmployerId(employerId);
	}
	
	
	public void saveOrUpdate(Job job) {
		jobRepository.save(job);
	}
	
	public void deleteById(long id) {
		jobRepository.deleteById(id);
	}
	
	public Date getPostedTimeByJobId(long id) {
		return jobRepository.findById(id).get().getPostedTime();
	}
	
	public List<Job> searchByCityAndDescription(String city, String description) {
		return jobRepository.findByCityAndDescription(city, description);
	}
	
	
	

}
