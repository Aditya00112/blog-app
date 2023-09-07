package com.chirantan.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chirantan.project.entities.Comment;
import com.chirantan.project.entities.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	List<Comment> findByPost(Post post);
//	List<Comment> findByPost(Post post);

}
