
package com.produkt.config;

import com.produkt.model.User;
import com.produkt.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsImp implements UserDetailsService {

	@Autowired
	private UserRepository reposit;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = reposit.getUserByEmail(email);

		if (user == null) {

			throw new UsernameNotFoundException("user not found");

		}

		CustomUserDetails userDetails = new CustomUserDetails(user);

		return userDetails;
	}

}
