package com.cegedim.react.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cegedim.react.domain.Backlog;
import com.cegedim.react.domain.Task;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>{
	Backlog findByProjectIdentifier(String identifier);
	List<Task> findBacklogById(String identifier);
}
