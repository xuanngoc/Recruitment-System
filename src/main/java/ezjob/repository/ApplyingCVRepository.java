package ezjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ezjob.model.ApplyingCV;


public interface ApplyingCVRepository extends JpaRepository<ApplyingCV, Long> {
	
	@Query(value = "SELECT * FROM APPLYING_CV WHERE JOB_ID = ? ORDER BY DATETIME DESC", nativeQuery = true)
	List<ApplyingCV> findByJobId(long id);
	
	@Query(value = "SELECT path_file_cv FROM APPLYING_CV WHERE CV_ID = ?", nativeQuery = true)
	String findPathFileCvById(long id);
	
	//List<ApplyingCV> findAllByOrderByDatetimeAsc();
}
