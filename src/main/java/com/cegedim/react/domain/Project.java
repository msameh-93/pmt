package com.cegedim.react.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message= "Cannot leave Name field empty")
	private String name;
	@NotBlank(message= "Cannot leave project identfier field empty")
	@Size(max= 5, min= 4, message="Identifier must be within 4-5 characters")
	@Column( updatable = false, unique= true)			//Enforced on DB column - Cannot be updated
	private String identifier;
	@NotBlank(message="Must provide project description")
	private String description;
	private String projectLeader;
	/****************************/
	@JsonFormat(pattern = "yyyy-mm-dd")	//@Jackson framework Format date field
	private Date startDate;
	@JsonFormat(pattern = "yyyy-mm-dd")	
	private Date endDate;
	@JsonFormat(pattern = "yyyy-mm-dd")	
	@Column(updatable= false)	//Will not set created at to null during project update
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-mm-dd")	
	private Date updatedAt;
	/***************************************************/
	//Project is owning side of relationship 
	//Project is referenced by id in backlog
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="project")
	@JsonIgnore
	private Backlog backlog;
	@ManyToOne
	@JsonIgnore
	private User user;
	/****************************************************/
	@PrePersist
	protected void prePersist() {
		this.createdAt= new Date();
	}
	@PreUpdate
	protected void preUpdate() {
		this.updatedAt= new Date();
	}
	public Project() {
		super();
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getIdentifier() {
		return identifier;
	}
	public final void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final Date getStartDate() {
		return startDate;
	}
	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public final Date getEndDate() {
		return endDate;
	}
	public final void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public final Backlog getBacklog() {
		return backlog;
	}
	public final void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	public final User getUser() {
		return user;
	}
	public final void setUser(User user) {
		this.user = user;
	}
	public final String getProjectLeader() {
		return projectLeader;
	}
	public final void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}
	
}
