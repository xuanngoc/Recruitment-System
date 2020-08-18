package ezjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import ezjob.config.ApplicationUserRole;
import ezjob.model.User;
import ezjob.repository.UserRegisterRepository;




@Service
public class UserRegisterService {
	
private UserRegisterRepository userRegisterRepository;

	
	@Autowired
	private void setUserRegisterRepository(UserRegisterRepository userRegisterRepository) {
		this.userRegisterRepository = userRegisterRepository;
	}
	
		
		
	
	public void saveOrUpdate(User user) {
		user.setUsername(user.getEmail());
		user.setRole(ApplicationUserRole.CANDIDATE.name());
		userRegisterRepository.save(user);
	}
	

}
