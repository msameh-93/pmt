package com.cegedim.react.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cegedim.react.domain.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	List<Task> findByProjectIdentifierOrderByPriority(String identifier);
	List<Task> findAll();
	Task findByProjectSequence(String projectSequence);
}
