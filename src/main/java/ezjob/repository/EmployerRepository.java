package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ezjob.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
