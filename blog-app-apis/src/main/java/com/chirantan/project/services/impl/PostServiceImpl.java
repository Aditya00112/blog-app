package com.chirantan.project.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chirantan.project.entities.Category;
import com.chirantan.project.entities.Post;
import com.chirantan.project.entities.User;
import com.chirantan.project.exceptions.ResourceNotFoundException;
import com.chirantan.project.payloads.PostDto;
import com.chirantan.project.payloads.PostResponse;
import com.chirantan.project.repositories.CategoryRepo;
import com.chirantan.project.repositories.PostRepo;
import com.chirantan.project.repositories.UserRepo;
import com.chirantan.project.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryID) {
		// get user
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		// get category
		Category category = this.categoryRepo.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryID));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<PostDto> postDtos = this.postRepo.findAll().stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}
	
	@Override
	public PostResponse getAllPostWitPaging(Integer pageNo, Integer pageSize,String sortBy,String sortDir) {
//		Pageable page= PageRequest.of(pageNo, pageSize);
		Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
//		if(sortDir.equalsIgnoreCase("desc")) {
//			sort=Sort.by(sortBy).descending();
//		}else {
//			sort=Sort.by(sortBy).ascending();
//		}
		Pageable page= PageRequest.of(pageNo, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(page);
		List<Post> posts =pagePost.getContent();
		System.out.println(posts);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse= new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}
	

	@Override
	public PostDto getPostById(Integer postId) {
		return this.modelMapper.map(this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId)), PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map(e -> this.modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String Keyword) {
		List<Post> posts= this.postRepo.findByTitleContaining(Keyword);
		List<PostDto> postDtos = posts.stream().map(e->this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	@Override
	public List<PostDto> searchPostQ(String Keyword) {
		List<Post> posts= this.postRepo.searchPostQ("%"+Keyword+"%"); // % for like command
		List<PostDto> postDtos = posts.stream().map(e->this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}


}
