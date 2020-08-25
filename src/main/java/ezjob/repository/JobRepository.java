package ezjob.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ezjob.model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

	@Query(value = "SELECT * FROM JOB WHERE EMPLOYER_ID = ?1", nativeQuery = true)
	public Page<Job> findJobByEmployerId(long id, Pageable pageable);
	
	  @Query(value = "select distinct j.* from job j "
			+ " inner join (select employer_id from employer e where city LIKE CONCAT('%',?1,'%')  ) e"
			+ " on j.employer_id = e.employer_id"
			+ " inner join jobs_skill_tags js"
			+ " on j.job_id = js.job_id"
			+ " inner join skill_tag s"
			+ " on js.skill_tag_id=s.skill_tag_id"
			+ " where ((j.description LIKE  CONCAT('%',?2,'%') ) or ( j.title LIKE CONCAT('%',?2,'%')) ) or (s.skill_tag_name = ?2)  ", nativeQuery = true)
	public List<Job> findByCityAndDescription(String city, String description);
	
	@Query(value = "SELECT * FROM JOB WHERE employer_id = ?1 AND title LIKE CONCAT('%',?2,'%')", nativeQuery = true)  
	public Page<Job> findByEmployerIdAndTitleContaining(long id, String title, Pageable pageable);
	
}
