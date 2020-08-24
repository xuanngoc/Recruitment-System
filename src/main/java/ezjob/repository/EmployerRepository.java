package ezjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import ezjob.model.Employer;
public interface EmployerRepository extends JpaRepository<Employer, Long> {

	@Query(value = "select * from employer where user_id = ?", nativeQuery = true)
	public Employer findByUserId(long id);

	  @Query(value = "select e.employer_id, company_name, total_job from employer e inner join (select employer_id, COUNT(employer_id) total_job from job " + 
	  		"  where closed = false group by employer_id) j on e.employer_id = j.employer_id " + 
	  		" order by total_job DESC limit 9 " , nativeQuery = true)
	 

	/*
	 * @Query("select new ezjob.model.dto.CompanyDTO(employerId, companyName, size) from Employer"
	 * )
	 */
	public List<Object> findCompanyNameTop9ByTotalJobPosted();

}
