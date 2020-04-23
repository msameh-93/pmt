package com.cegedim.react.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
public class User implements UserDetails{	//to be able to customize user in user detail service
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message="Please enter a valid email address")
	@NotBlank(message= "Cannot leave Email field empty")
	@Column(unique= true)
	private String username;
	@NotBlank(message= "Must provide full name")
	private String fullName;
	@NotBlank(message= "Must provide password")
	private String password;
	@Transient	//Not persisted, only used for confirmation
	private String confirmPassword;
	private Date createdAt;
	private Date updatedAt;
	
	//Authorization
	private String role;
	//Relationship with projects (One user, many projects)
	@OneToMany(cascade= CascadeType.REFRESH, fetch= FetchType.EAGER, mappedBy= "user", orphanRemoval = true)
	private List<Project> projects= new ArrayList<>();
	
	/*********User Details interface methods***************/
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {	//roles
		return null;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {	//Main logic
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {	//Additional logic (eg: user account subscription ends)
		return true;	
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {	//Additional logic
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {	//Additional logic
		return true;
	}
	/********Utilities*********/
	@PrePersist
	public void prepersist() {
		this.createdAt= new Date();
	}
	@PreUpdate
	public void preupdate() {
		this.updatedAt= new Date();
	}
	
	public User() {
		
	}

	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getUsername() {
		return username;
	}
	public final void setUsername(String username) {
		this.username = username;
	}
	public final String getFullName() {
		return fullName;
	}
	public final void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final String getConfirmPassword() {
		return confirmPassword;
	}
	public final void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public final Date getCreatedAt() {
		return createdAt;
	}
	public final void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public final Date getUpdatedAt() {
		return updatedAt;
	}
	public final void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public final String getRole() {
		return role;
	}
	public final void setRole(String role) {
		this.role = role;
	}
	public final List<Project> getProjects() {
		return projects;
	}
	public final void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
}
