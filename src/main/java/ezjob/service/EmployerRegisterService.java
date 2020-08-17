package ezjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.EmployerRegister;
import ezjob.repository.EmployerRegisterRepository;

@Service
public class EmployerRegisterService {
	
	
	private EmployerRegisterRepository employerRegisterRepository;
	
	@Autowired
	private void setEmployerRegisterRepository(EmployerRegisterRepository employerRegisterRepository) {
		this.employerRegisterRepository = employerRegisterRepository;
	}
	
	public List<EmployerRegister> getAllEmployerRegisters() {
		return employerRegisterRepository.findAll();
	}
	
	public List<EmployerRegister> getAllEmployerRegistersPending() {
		return employerRegisterRepository.findByPendingOrderBySentAtDesc(true);
	}
	
	public EmployerRegister getEmployerRegisterById(long id) {
		return employerRegisterRepository.findById(id).get();
	}
	
	public void acceptRequestPending(long id) {
		EmployerRegister er = employerRegisterRepository.findById(id).get();
		if (er != null) {
			er.setPending(false);
			employerRegisterRepository.save(er);
		}
	}
	
	public void saveOrUpdate(EmployerRegister employerRegister) {
		employerRegisterRepository.save(employerRegister);
	}
	
	public void deleteEmployerRegisterById(long id) {
		employerRegisterRepository.deleteById(id);
	}
	
}
