package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import ezjob.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

	@Query(value = "select * from employer where user_id = ?", nativeQuery = true)
	public Employer findByUserId(long id);
}
