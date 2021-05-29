package ezjob.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Collection<? extends GrantedAuthority> userAuthority = authentication.getAuthorities();
		if (userAuthority.contains(new SimpleGrantedAuthority(ApplicationUserRole.MANAGER.name()))) {
			response.sendRedirect("/management/");
		} else if (userAuthority.contains(new SimpleGrantedAuthority(ApplicationUserRole.EMPLOYER.name()))) {
			response.sendRedirect("/employer/");
		} else {
			response.sendRedirect("/");
		}		
	}

}
