package com.techlabs.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.techlabs.app.dto.BlogDto;
import com.techlabs.app.service.BlogService;
import com.techlabs.app.service.BlogServiceImpl;
import com.techlabs.app.service.CommentService;
import com.techlabs.app.service.CommentServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
	
	Logger logger=LoggerFactory.getLogger(BlogController.class);

	private final BlogService blogService;
	private final CommentService commentService;

	public BlogController(BlogServiceImpl blogService, CommentServiceImpl commentService) {
		this.blogService = blogService;
		this.commentService = commentService;
	}

	@GetMapping
	public ResponseEntity<List<BlogDto>> getAllBlogs() {
		logger.trace("Get All blogs method started");
		logger.info("Get All blogs method started");
		List<BlogDto> blogs = blogService.getAllBlogs();
		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BlogDto> saveBlog(@Valid @RequestBody BlogDto blogDto) {
		logger.trace("Save blogs method started");
		BlogDto savedBlog = blogService.saveBlog(blogDto);
		return new ResponseEntity<>(savedBlog, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<BlogDto> updateBlog(@Valid @RequestBody BlogDto blogDto) {
		logger.trace("Update blogs method started");
		BlogDto savedBlog = blogService.updateBlog(blogDto);
		return new ResponseEntity<>(savedBlog, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BlogDto> getBlogBYId(@PathVariable(name = "id") int id) {
		logger.trace("Get  blog by Id  method started");
		BlogDto dto = blogService.getBlogBYId(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<String> deleteblog(@PathVariable(name = "id") int id) {
		logger.trace("Delete blogs method started");
		String deleted = blogService.deleteblog(id);
		return new ResponseEntity<>(deleted, HttpStatus.OK);

	}
	
	@PutMapping("/assign/comment/{cId}/blog/{bId}")
	public ResponseEntity<BlogDto> assignCommentToABlog(@PathVariable(name = "bId") int bId, @PathVariable(name = "cId") int cId) {
		logger.trace("Assign comment to a  blogs method started");
		BlogDto dto = blogService.assignCommentToABlog(cId,bId);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	@PutMapping("/remove/comment/{cId}/Blog/{bId}")
	public ResponseEntity<BlogDto> removeCommentToABlog(@PathVariable(name = "bId") int bId, @PathVariable(name = "cId") int cId) {
		
		logger.trace("Remove comment from a  blogs method started");
		BlogDto dto = blogService.removeCommentToABlog(cId,bId);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
	
	
	
}
