package com.chirantan.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chirantan.project.config.AppConstants;
import com.chirantan.project.payloads.ApiResponse;
import com.chirantan.project.payloads.PostDto;
import com.chirantan.project.payloads.PostResponse;
import com.chirantan.project.services.FileService;
import com.chirantan.project.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	// create mutiple posts
		@PostMapping("/user/{userId}/category/{categoryId}/posts/multiple")
		public ResponseEntity<ApiResponse> createMultiplePosts( @PathVariable Integer userId,
				@PathVariable Integer categoryId) {
			PostDto postDto=new PostDto();
			for(int i =0;i<15;i++) {
				postDto.setTitle("Title->>" + i);
				postDto.setContent("Content->>"+i);
				this.postService.createPost(postDto, userId, categoryId);
			}
			
			return new  ResponseEntity<ApiResponse>(new ApiResponse("Data Created ",true), HttpStatus.CREATED);
		}

	// get posts by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> postDtos = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// get posts by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPost() {
		List<PostDto> postDtos = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	// get all posts with paging concept
		@GetMapping("/postswithPaging")
		public ResponseEntity<PostResponse> getAllPostWithPaging(
				@RequestParam(value="size",required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
				@RequestParam(value="no",required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNo,
				@RequestParam(value="sortBy",required = false, defaultValue = AppConstants.SORT_BY) String sortBy,
				@RequestParam(value="sortDir",required = false, defaultValue = AppConstants.SORT_DIR) String sortDir
				) {
			PostResponse postResponse = this.postService.getAllPostWitPaging(pageNo,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		}


	// get  post detail by ID
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getAllPost(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully",true),HttpStatus.OK);
	}
	
	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
		
		//search post with title
		@GetMapping("/posts/search/{keyword}")
		public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyword") String keyword) {
			List<PostDto> postDtos = this.postService.searchPost(keyword);
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
		// search post with title
		@GetMapping("/posts/searchQ/{keyword}")
		public ResponseEntity<List<PostDto>> searchPostQ(@PathVariable("keyword") String keyword) {
			List<PostDto> postDtos = this.postService.searchPostQ(keyword);
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
		// post image upload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable("postId") Integer postId
				) throws IOException{
			PostDto postDto = this.postService.getPostById(postId);
			String fileName = this.fileService.uploadImage(path, image);
			postDto.setImageName(fileName);
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		//method to serve files
		@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
			
		}
				
	

}
