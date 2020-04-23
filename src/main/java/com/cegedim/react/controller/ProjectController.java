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

import com.cegedim.react.domain.Project;
import com.cegedim.react.services.MapValidationError;
import com.cegedim.react.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
	@Autowired
	private ProjectService projectServ;		//injected project service layer
	@Autowired
	private MapValidationError mapValidator;
	
	@PostMapping
	public ResponseEntity<?> createProject(@RequestBody@Valid Project project, BindingResult bindingResult, Principal principal) {	
		//Response entity gives more control on http response object
		//binding result used to validate sent object FROM CLIENT
		//principal==owner of token - Assigned in oncePerRequest filter
		if(bindingResult.hasErrors()) {
			return mapValidator.validate(bindingResult);
		}
		//saveorUpdateProject(new created project, (current logged in user) relationship)
		return new ResponseEntity<Project>(projectServ.saveorUpdateProject(project, principal.getName()), HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<?> getAllprojects(Principal principal) {
		
		return new ResponseEntity<Iterable<Project>>(projectServ.getAllProjects(principal.getName()), HttpStatus.OK);
	}
	@GetMapping("/{identifier}")	//identifier NOT ID
	public ResponseEntity<?> getProject(@PathVariable("identifier") String identifier, Principal principal) {

		return new ResponseEntity<Project>(projectServ.getProjectById(identifier.toUpperCase(), principal.getName()), HttpStatus.OK);
	}
	@DeleteMapping("/{identifier}")
	public ResponseEntity<?> deleteProject(@PathVariable("identifier") String identifier, Principal principal) {
		projectServ.deleteProjectById(identifier, principal.getName());
		return new ResponseEntity<String>("Project with ID: '" + identifier.toUpperCase() +"' was deleted successfully!", HttpStatus.OK);
	}
	//JPA updates or saves auto if ID matches an entry in DB
}
