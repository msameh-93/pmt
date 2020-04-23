package com.cegedim.react.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegedim.react.domain.Backlog;
import com.cegedim.react.domain.Project;
import com.cegedim.react.domain.User;
import com.cegedim.react.exceptions.ProjectIdException;
import com.cegedim.react.repositories.BacklogRepository;
import com.cegedim.react.repositories.ProjectRepository;
import com.cegedim.react.repositories.UserRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepo;	//Using injected repository layer
	@Autowired
	private BacklogRepository backlogRepo;
	@Autowired
	private UserRepository userRepo;
	
	public Project saveorUpdateProject(Project project, String principalName) {
		//Check project is in DB and belongs to user
		//not null: updating a project
		if(project.getId()!=null) {
			//Check if found and if it exists in DB
			 Project existingProject= projectRepo.getById(project.getId());
			if(existingProject==null) {
				throw new ProjectIdException("No project with id: " + project.getId() + " exsists in DB!");
			}
			getProjectById(existingProject.getIdentifier().toUpperCase(), principalName);
		}
		//DB Exception handling
		try {			
			//Set project leader relationship
			User user= userRepo.findByUsername(principalName);
			project.setUser(user);
			project.setProjectLeader(principalName);
			
			project.setId(project.getId());
			if(project.getId()==null) {									//if first time creating project
				Backlog backlog= new Backlog();							//instantiate new backlog instance
				project.setBacklog(backlog);							//set new backlog instance to this project
				backlog.setProject(project);							//set project instance to this backlog
				backlog.setProjectIdentifier(project.getIdentifier().toUpperCase());	//set backlog instance to this project
			}
			else {
				project.setBacklog(backlogRepo.findByProjectIdentifier(project.getIdentifier().toUpperCase()));
			}
			
			return projectRepo.save(project);			//Try persisting
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getIdentifier() + "' already exists");
		}
	}
	public Iterable<Project> getAllProjects(String principalName) {	
		
		return projectRepo.findAllByProjectLeader(principalName);
	}
	public Project getProjectById(String identifier, String principalName) {

		Project project= projectRepo.findByIdentifier(identifier.toUpperCase());
		
		if(project==null) {
			throw new ProjectIdException("No project with '"+ identifier +"' exists");
		}
		if(!project.getProjectLeader().equals(principalName)) {
			throw new ProjectIdException("This Project does not belong to this user");
		}
		
		return project;
	}
	public void deleteProjectById(String identifier, String principalName) {

		projectRepo.delete(getProjectById(identifier.toUpperCase(), principalName));	//Supplied by CrudRepo
	}
}
