package ezjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.ApplyingCV;
import ezjob.repository.ApplyingCVRepository;

@Service
public class ApplyingCVService {
	
	private ApplyingCVRepository appyingCVRepository;
	
	@Autowired
	public void setAppyingCVRepository(ApplyingCVRepository appyingCVRepository) {
		this.appyingCVRepository = appyingCVRepository;
	}
	
	public String getPathFileCVById(long id) {
		return appyingCVRepository.findPathFileCvById(id);
	}
	
	public List<ApplyingCV> getApplyingCVsByJobId(long id) {
		return appyingCVRepository.findByJobId(id);
	}
	
	public void saveOrUpdate(ApplyingCV cv) {
		appyingCVRepository.save(cv);
	}
	
}
