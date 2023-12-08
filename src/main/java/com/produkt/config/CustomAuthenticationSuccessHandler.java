
package com.produkt.config;

/**
 *
 * @author GhulaM Murtaza
 */
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override public void
	onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, Authentication
			authentication) throws IOException, jakarta.servlet.ServletException {

		String defaultTargetUrl = "/user/home/0"; // Default to /user/index

		for (GrantedAuthority authority : authentication.getAuthorities()) { if
			("ROLE_ADMIN".equals(authority.getAuthority())) { // For admin users, change the default target URL to /admin/index 
			defaultTargetUrl = "/admin/index";
			break; } }

		setDefaultTargetUrl(defaultTargetUrl); super.onAuthenticationSuccess(request,
				response, authentication); }
}
