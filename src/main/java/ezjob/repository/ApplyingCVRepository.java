package ezjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ezjob.model.ApplyingCV;


public interface ApplyingCVRepository extends JpaRepository<ApplyingCV, Long> {
	
	@Query(value = "SELECT * FROM APPLYING_CV WHERE JOB_ID = ? ORDER BY DATETIME DESC", nativeQuery = true)
	Page<ApplyingCV> findByJobId(long id, Pageable pageable);
	
	@Query(value = "SELECT * FROM APPLYING_CV WHERE JOB_ID = ?1 AND FULLNAME LIKE CONCAT('%', ?2, '%')", nativeQuery = true)
	Page<ApplyingCV> findByJobIdAndTitleContainning(long id, String name, Pageable pageable);
	
	@Query(value = "SELECT path_file_cv FROM APPLYING_CV WHERE CV_ID = ?", nativeQuery = true)
	String findPathFileCvById(long id);
	
	//List<ApplyingCV> findAllByOrderByDatetimeAsc();
}
