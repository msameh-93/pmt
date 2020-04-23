package com.cegedim.react.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegedim.react.domain.User;
import com.cegedim.react.exceptions.UserNameException;
import com.cegedim.react.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {//Manages UserDetails logic

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepo.findByUsername(username);
		if(user==null) {
			throw new UserNameException("User Not Found");
		}
		return user;
	}	
	//Manages DB transactions
	@Transactional
	public User loadUserById(Long id) {
		User user= userRepo.getById(id);
		if(user==null) {
			throw new UserNameException("User Not Found");
		}
		return user;
	}

}
