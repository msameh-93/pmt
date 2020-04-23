package com.cegedim.react.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

//Many asks with one backlog
@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable= false)
	private String projectSequence;	//Identify individual project tasks
	@Column(updatable= false)
	private String projectIdentifier;
	
	@NotBlank(message="Please include project summary")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "backlog_id", updatable= false, nullable=false)
	@JsonIgnore
	private Backlog backlog;
	
	
	public final Backlog getBacklog() {
		return backlog;
	}
	public final void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	@PrePersist
	protected void onCreate() {
		this.createdAt= new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt= new Date();
	}
	public Task() {
		super();
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getProjectSequence() {
		return projectSequence;
	}
	public final void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}
	public final String getSummary() {
		return summary;
	}
	public final void setSummary(String summary) {
		this.summary = summary;
	}
	public final String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}
	public final void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	public final String getStatus() {
		return status;
	}
	public final void setStatus(String status) {
		this.status = status;
	}
	public final Integer getPriority() {
		return priority;
	}
	public final void setPriority(Integer priority) {
		this.priority = priority;
	}
	public final String getProjectIdentifier() {
		return projectIdentifier;
	}
	public final void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public final Date getDueDate() {
		return dueDate;
	}
	public final void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

}
