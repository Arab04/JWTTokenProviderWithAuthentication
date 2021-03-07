package com.app.polling.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.polling.model.User;
import com.app.polling.reprository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepo userrepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByUsernameOrEmail(username, username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with this email or username: "+username));
		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadByUserId(Long id) {
		User user = userrepo.findById(id)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with this id "+id));
		return UserPrincipal.create(user);
	}
}
