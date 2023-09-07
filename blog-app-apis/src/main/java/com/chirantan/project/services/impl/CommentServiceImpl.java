package com.chirantan.project.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chirantan.project.entities.Comment;
import com.chirantan.project.entities.Post;
import com.chirantan.project.entities.User;
import com.chirantan.project.exceptions.ResourceNotFoundException;
import com.chirantan.project.payloads.CommentDto;
import com.chirantan.project.repositories.CommentRepo;
import com.chirantan.project.repositories.PostRepo;
import com.chirantan.project.repositories.UserRepo;
import com.chirantan.project.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		User  user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Comment comment= this.modelMapper.map(commentDto, Comment.class);
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		comment.setUser_comment(user);
		Comment newComment = this.commentRepo.save(comment);
		return this.modelMapper.map(newComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment= this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment ID", commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDto updateComment(CommentDto comment, Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

}
