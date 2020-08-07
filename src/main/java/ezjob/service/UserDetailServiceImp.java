package ezjob.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ezjob.config.ApplicationUserRole;
import ezjob.model.Employer;
import ezjob.model.EmployerRegister;
import ezjob.model.User;
import ezjob.model.UserPrincipal;
import ezjob.repository.EmployerRepository;
import ezjob.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	
	
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private EmployerService employerService;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(user, passwordEncoder);
	}
	
	public void createEmployerUser(EmployerRegister employerRegister) {
		User user = new User();
		user.setUsername(employerRegister.getEmail());
		user.setPassword(UUID.randomUUID().toString()); // not hash password yet
		user.setEmail(employerRegister.getEmail());
		user.setRole(ApplicationUserRole.EMPLOYER.name());
		user.setPhone(employerRegister.getPhone());
		userRepository.save(user);
		
		Employer employer = new Employer();
		employer.setCompanyName(employerRegister.getCompanyName());
		employer.setCity(employerRegister.getCity());
		employer.setUser(user);
		employerService.saveOrUpdate(employer);
		
	}
}
