package com.cegedim.react.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegedim.react.domain.Task;
import com.cegedim.react.services.MapValidationError;
import com.cegedim.react.services.TaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private MapValidationError mapValidator;
	
	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> createTask(
			@RequestBody@Valid Task task, BindingResult bindingResult, 
			@PathVariable("projectIdentifier") String projectIdentifier, Principal principal) {
		if(bindingResult.hasErrors()) {
			return mapValidator.validate(bindingResult);
		}
		
		return new ResponseEntity<Task>(taskService.saveOrUpdateProjectTask(task, projectIdentifier.toUpperCase(), principal.getName()), HttpStatus.OK);
	}
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getTasksbybacklog(@PathVariable("projectIdentifier")String projectIdentifier, Principal principal) {
		return new ResponseEntity<Iterable<Task>>(taskService.getAllTasks(projectIdentifier, principal.getName()), HttpStatus.OK);
	}
	
	@GetMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> getTaskbySequence(
			@PathVariable("projectIdentifier")String projectIdentifier, 
			@PathVariable("projectSequence")String projectSequence, Principal principal) {
		
		return new ResponseEntity<Task>(taskService.getTaskByProjectSequence(projectIdentifier, projectSequence, principal.getName()), HttpStatus.OK);
	}
	@PostMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> createTask(@RequestBody@Valid Task task, 
				BindingResult bindingResult, 
				@PathVariable("projectIdentifier") String projectIdentifier, 
				@PathVariable("projectSequence")String projectSequence, Principal principal) {
		if(bindingResult.hasErrors()) {
			return mapValidator.validate(bindingResult);
		}
		
		return new ResponseEntity<Task>(taskService.updateTask(
				task, projectIdentifier.toUpperCase(), projectSequence.toUpperCase(), principal.getName()), HttpStatus.OK);
	}
	@DeleteMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> deleteTask(
			@PathVariable("projectIdentifier") String projectIdentifier, 
			@PathVariable("projectSequence")String projectSequence, Principal principal) {
		taskService.deleteTaskById(projectIdentifier, projectSequence, principal.getName());
		
		return new ResponseEntity<String>("Task Deleted with project sequence '"+projectSequence+"' Successfully", HttpStatus.OK);
	}
}
