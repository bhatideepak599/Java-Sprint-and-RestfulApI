package com.techlabs.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.BlogDto;
import com.techlabs.app.dto.CommentDto;
import com.techlabs.app.service.BlogService;
import com.techlabs.app.service.BlogServiceImpl;
import com.techlabs.app.service.CommentService;
import com.techlabs.app.service.CommentServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	private final BlogService blogService;
	private final CommentService commentService;

	public CommentController(BlogServiceImpl blogService, CommentServiceImpl commentService) {
		this.blogService = blogService;
		this.commentService = commentService;
	}
	
	@GetMapping
	public ResponseEntity<List<CommentDto>> getAllComments() {
		List<CommentDto> comments = commentService.getAllComments();
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<CommentDto> saveBlog(@Valid @RequestBody CommentDto commentDto) {
		CommentDto savedComment = commentService.saveComment(commentDto);
		return new ResponseEntity<>(savedComment, HttpStatus.OK);
	}
	

	@GetMapping("/byBlogId/{id}")
	public ResponseEntity<List<CommentDto>> getCommentByBlogId(@PathVariable(name = "id") int id) {
		List<CommentDto> comments = commentService.getCommentByBlogId(id);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	

}
