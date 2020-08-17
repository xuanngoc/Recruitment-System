package ezjob.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ezjob.service.UserDetailServiceImp;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private UserDetailServiceImp userDetailService;
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	public void setUserDetailService(UserDetailServiceImp userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@Autowired
	public void setAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/job/**", "/login", "/employer-register", "/js/**", "/img/**", "/css/**", "/webjars/**")
					.permitAll()
				.antMatchers("/management/**").hasAuthority(ApplicationUserRole.MANAGER.name())
				.antMatchers("/employer/**").hasAuthority(ApplicationUserRole.EMPLOYER.name())
				.antMatchers("/authenticate/**").hasAuthority(ApplicationUserRole.USER.name())
					.anyRequest()
					.authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.successHandler(authenticationSuccessHandler)
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());	
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
