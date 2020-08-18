package ezjob.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ezjob.model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

	public List<Job> findJobByEmployerEmployerId(long id);
	
	@Query(value = "select j.* from job j"
			+ " inner join (select employer_id from employer e where city = ?1 ) e"
			+ " on j.employer_id = e.employer_id"
			+ " where j.description LIKE %?2%", nativeQuery = true)
	public List<Job> findByCityAndDescription(String city, String description);
}
