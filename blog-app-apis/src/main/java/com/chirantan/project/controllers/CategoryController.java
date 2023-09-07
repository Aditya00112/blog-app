package com.chirantan.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chirantan.project.payloads.ApiResponse;
import com.chirantan.project.payloads.CategoryDto;
import com.chirantan.project.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid@RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid@RequestBody CategoryDto categoryDto,@PathVariable Integer id){
		CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successully!!",true),HttpStatus.OK);
	}
	
	//get single
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
		CategoryDto categoryDto=this.categoryService.getCategory(id);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.ACCEPTED);
	}
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getallCategory(){
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getCategories(),HttpStatus.ACCEPTED);
	}

}
