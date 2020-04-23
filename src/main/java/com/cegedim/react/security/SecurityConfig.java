package com.cegedim.react.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cegedim.react.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(		//Role based authorization
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter {	//provides default security configuration, override to customize
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private CustomUserDetailService customUserDetailService;	//used by authentication manager
	@Autowired
	private BCryptPasswordEncoder bCryptPass;
	
	@Bean
	public JwtAuthFilter getJwtAuthFilter() {
		return new JwtAuthFilter();
	}
	
	//Create Authentication manager bean
	//Configured by configure(AuthenticationManagerBuilder auth)
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	//Configure Authentication Manager builder (to enable authentication of users against multiple identity management sources.)
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPass);
		}
	
	//Configures HTTP authentication and authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {	//protect API
        http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .headers().frameOptions().sameOrigin() 		//To enable H2 Database
        .and()
        .authorizeRequests()						//Authorization of http requests
        .antMatchers(	//homepage loading
        		"/",                         
        		"/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.json",
                "/**/*.map",
                "/**/*.css",
                "/**/*.js").permitAll()
        .antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll()	//authorize signup endpoint
        .antMatchers(SecurityConstants.H2URL).permitAll()
        .anyRequest().authenticated();				//Authenticate rest
        
        //Add Filter
        http.addFilterBefore(getJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
