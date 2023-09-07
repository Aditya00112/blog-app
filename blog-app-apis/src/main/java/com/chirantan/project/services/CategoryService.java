package com.chirantan.project.services;

import java.util.List;

import com.chirantan.project.payloads.CategoryDto;

public interface CategoryService {

	//note by default all class in interface is public and abstract
	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	void deleteCategory(Integer categoryId);

	// get
	CategoryDto getCategory(Integer categoryId);
	// getall
	
	List<CategoryDto> getCategories();

}
