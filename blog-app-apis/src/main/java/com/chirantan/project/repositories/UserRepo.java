package com.chirantan.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chirantan.project.entities.User;

public interface UserRepo  extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

}
