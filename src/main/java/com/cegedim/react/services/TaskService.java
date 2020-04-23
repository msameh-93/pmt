package com.cegedim.react.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegedim.react.domain.Backlog;
import com.cegedim.react.domain.Task;
import com.cegedim.react.exceptions.ProjectIdException;
import com.cegedim.react.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepo;
	@Autowired
	private ProjectService projectService;
	
	public Task saveOrUpdateProjectTask(Task task, String projectIdentifier, String principalName) {
		
		//gets backlog by checking if project exists and belongs to the logged in user (Principal)
		Backlog backlog= projectService.getProjectById(projectIdentifier, principalName).getBacklog();
		task.setBacklog(backlog);		
		//Set sequence number
		Integer backlogSequence= backlog.getPTSequence();
		backlogSequence++;
		backlog.setPTSequence(backlogSequence);
		task.setProjectSequence(projectIdentifier+"-"+backlogSequence);	//eg: ASDAS-1,ASDAS-2
		task.setProjectIdentifier(projectIdentifier);			//keep track of project
		/***********************/
		if(task.getPriority()==null || task.getPriority()==0) {
			task.setPriority(3);		//3 is low prio, 1 is highest prio
		}
		if(task.getStatus()==null || task.getStatus().equals("")) {
			task.setStatus("TO_DO");		
		}
		
		return taskRepo.save(task);
	}
	
	public Iterable<Task> getAllTasks(String identifier, String principalName) {
		//Check project exists and belongs to user
		projectService.getProjectById(identifier, principalName);

		Iterable<Task> tasks= taskRepo.findByProjectIdentifierOrderByPriority(identifier);
		return tasks;
	}

	public Task getTaskByProjectSequence(String projectIdentifier, String projectSequence, String principalName) {
		
		Backlog backlog= projectService.getProjectById(projectIdentifier, principalName).getBacklog();
		if(backlog==null) {
			throw new ProjectIdException("No project with identifier '"+ projectIdentifier.toUpperCase() +"' exists");
		}
		Task task= taskRepo.findByProjectSequence(projectSequence);
		if(task==null) {
			throw new ProjectIdException("No task with Project sequence '"+ projectSequence.toUpperCase() +"' exists");
		}

		if(!backlog.getProjectIdentifier().equals(task.getProjectIdentifier())) {
			throw new ProjectIdException("No task with Project sequence '"+ projectSequence.toUpperCase() +"' corresponds to  a project with identifier '"+ projectIdentifier.toUpperCase());
		}
		return task;
	}
	public Task updateTask(Task updatedTask, String projectIdentifier, String projectSequence, String principalName) {

		Task task= getTaskByProjectSequence(projectIdentifier, projectSequence, principalName);

		updatedTask.setId(task.getId());
		
		return taskRepo.save(updatedTask);
	}
	public void deleteTaskById(String projectIdentifier, String projectSequence, String principalName) {
		Task task= getTaskByProjectSequence(projectIdentifier, projectSequence, principalName);
		
		taskRepo.delete(task);
	}
}
