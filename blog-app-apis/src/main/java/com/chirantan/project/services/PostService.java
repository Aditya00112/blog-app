package com.chirantan.project.services;

import java.util.List;

import com.chirantan.project.payloads.PostDto;
import com.chirantan.project.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryID);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);

	//delete
	 void deletePost(Integer postId);
	//get all post
	 List<PostDto> getAllPost();
	 
	//get all post
	PostResponse getAllPostWitPaging(Integer pageSize, Integer pageNo,String sortBy,String sortDir);
	 
	 //get Single post
	 PostDto getPostById(Integer postId);
	 
	 //get all posts by category
	 List<PostDto> getPostsByCategory(Integer categoryId);
	 
	 //get all posts by User
	 List<PostDto> getPostsByUser(Integer userId);
	 
	 //search Posts
	 List<PostDto> searchPost(String Keyword);
	 
	 //search posts using query
	 List<PostDto> searchPostQ(String Keyword);
	
}
