package com.cegedim.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ReactApplication {
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {		//Create password encoder bean (used in user service)
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(ReactApplication.class, args);
	}

}
