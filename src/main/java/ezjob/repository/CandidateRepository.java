package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ezjob.model.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	@Query(value="select * from CANDIDATE WHERE USER_ID=? ", nativeQuery = true)
	public Candidate findByUserId(long id);
	
	
          
}
