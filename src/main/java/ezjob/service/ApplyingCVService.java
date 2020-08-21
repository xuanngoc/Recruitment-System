package ezjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.ApplyingCV;
import ezjob.model.Job;
import ezjob.repository.ApplyingCVRepository;

@Service
public class ApplyingCVService {
	
	private ApplyingCVRepository appyingCVRepository;
	
	@Autowired
	public void setAppyingCVRepository(ApplyingCVRepository appyingCVRepository) {
		this.appyingCVRepository = appyingCVRepository;
	}
	
	public void saveOrUpdate(ApplyingCV cv) {
		appyingCVRepository.save(cv);
	}
	
}
