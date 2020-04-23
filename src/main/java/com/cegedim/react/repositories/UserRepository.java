package com.cegedim.react.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cegedim.react.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	User getById(Long id);		//findById returns Optional<User>
}
