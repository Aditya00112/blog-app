package com.chirantan.project.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chirantan.project.entities.Category;
import com.chirantan.project.exceptions.ResourceNotFoundException;
import com.chirantan.project.payloads.CategoryDto;
import com.chirantan.project.repositories.CategoryRepo;
import com.chirantan.project.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat=this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List <Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> catDtos=categories.stream().map(category-> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}
	

}
