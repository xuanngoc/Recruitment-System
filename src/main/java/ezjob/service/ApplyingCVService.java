package ezjob.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<ApplyingCV> getApplyingCVsByJobId(long id, Pageable pageable) {
		return appyingCVRepository.findByJobId(id, pageable);
	}
	
	public Page<ApplyingCV> getApplyingCVByJobIdAndTitleContainning(long id, String name, Pageable pageable) {
		return appyingCVRepository.findByJobIdAndTitleContainning(id, name, pageable);
	}
	
	public void saveOrUpdate(ApplyingCV cv) {
		appyingCVRepository.save(cv);
	}
	
}
