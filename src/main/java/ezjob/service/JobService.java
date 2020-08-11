package ezjob.service;

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
	
}