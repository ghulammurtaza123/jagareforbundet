
package com.produkt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

	 AuthenticationManager authenticationManager;
	
	@Bean
	public UserDetailsService getDetailsService() {

		return new UserDetailsImp();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authenticate = new DaoAuthenticationProvider();
		authenticate.setUserDetailsService(this.getDetailsService());
		authenticate.setPasswordEncoder(this.passwordEncoder());

		return authenticate;

	}

	/// Configuration methods

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.authenticationProvider(authenticationProvider());
	 * 
	 * }
	 */

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
       // authenticationManager = authenticationManagerBuilder.build();
		http.authorizeHttpRequests((authorizeHttpRequests) ->
        {
			try {
				authorizeHttpRequests.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/**").permitAll().and().formLogin(f -> f.loginPage("/signin").loginProcessingUrl("/dologin")
						.successHandler(new CustomAuthenticationSuccessHandler())).csrf(AbstractHttpConfigurer::disable);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		});
		return http.build();
	}

}
