package com.chirantan.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chirantan.project.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	

}
