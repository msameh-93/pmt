package com.cegedim.react.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cegedim.react.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
	@Override
	Iterable<Project> findAll();
	Iterable<Project> findAllByProjectLeader(String projectLeader);
	Project findByIdentifier(String identifier);
	Project getById(Long id);
}
