package com.cegedim.react.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cegedim.react.domain.User;
import com.cegedim.react.exceptions.UserNameException;
import com.cegedim.react.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder bcryptPass;
	
	public User saveUser(User user) {
		try {
			
			user.setPassword(bcryptPass.encode(user.getPassword()));	//bCrypt the password
			return userRepo.save(user); 
			
		} catch(Exception e) {
			throw new UserNameException("User '" + user.getUsername()+ "' already exists!");
		}
	}
	
	
}
