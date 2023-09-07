package com.chirantan.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chirantan.project.entities.User;
import com.chirantan.project.exceptions.ResourceNotFoundException;
import com.chirantan.project.repositories.UserRepo;

@Service
public class CustomUserDetailService  implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading user from database by username (username is email)
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Username(Email)", "Email ID: "+ username, 0));
		return user;
	}

}
