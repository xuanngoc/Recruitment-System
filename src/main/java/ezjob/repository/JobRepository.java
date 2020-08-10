package ezjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ezjob.model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

	public List<Job> findJobByEmployerEmployerId(long id);
}
