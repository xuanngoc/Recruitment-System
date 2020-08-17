package ezjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ezjob.model.EmployerRegister;

public interface EmployerRegisterRepository extends JpaRepository<EmployerRegister, Long> {
	
	public List<EmployerRegister> findByPendingOrderBySentAtDesc(boolean isPending);
}
