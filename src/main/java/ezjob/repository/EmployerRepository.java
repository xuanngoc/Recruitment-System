package ezjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import ezjob.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

	@Query(value = "select * from employer where user_id = ?", nativeQuery = true)
	public Employer findByUserId(long id);
	
	@Query(value = "select company_name from employer e inner join (select employer_id, COUNT(employer_id) total_job from job group by employer_id) j on e.employer_id = j.employer_id "
			+ "order by total_job DESC limit 9", nativeQuery = true)
	public List<String> findCompanyNameTop9ByTotalJobPosted();
}
