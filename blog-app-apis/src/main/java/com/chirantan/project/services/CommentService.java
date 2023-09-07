package com.chirantan.project.services;

import java.util.List;

import com.chirantan.project.payloads.CommentDto;

public interface CommentService {
	
	//create
	CommentDto createComment(CommentDto comment,Integer userId,Integer postId);
	//delete
	void deleteComment(Integer commentId);
	//update
	CommentDto updateComment(CommentDto comment,Integer commentId);
	//get all comments
	List<CommentDto> getAllComments();

}
