package com.chirantan.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chirantan.project.entities.Category;
import com.chirantan.project.entities.Post;
import com.chirantan.project.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	@Query("select p from Post p where p.title like :key")
	List<Post> searchPostQ(@Param("key")String title);

}
