package com.practise.cafe.security;

import java.util.ArrayList;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practise.cafe.repo.UserRepo;

import org.springframework.security.core.userdetails.User;


@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	private com.practise.cafe.model.entity.User userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		userDetails=userRepo.findByEmail(username).get();
		if(!Objects.isNull(userDetails)) {
			return new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found");
		}

	}
	public com.practise.cafe.model.entity.User getUserDetails(){
		return userDetails;
	}

}