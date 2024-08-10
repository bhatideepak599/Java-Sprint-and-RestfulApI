package com.techlabs.app.service;

import java.util.List;
import com.techlabs.app.dto.CommentDto;
import com.techlabs.app.entity.Blog;
import com.techlabs.app.entity.Comment;

import jakarta.validation.Valid;

public interface CommentService {
	public Comment commentDtoToComment(CommentDto commentDto, Blog blog);

	public CommentDto commentToCommentDto(Comment comment);

	public CommentDto saveComment(@Valid CommentDto commentDto);

	public List<CommentDto> getAllComments();

	public List<CommentDto> getCommentByBlogId(int id);

	public String deleteComment(int id);

	public Blog getBlogByCommentId(int id);
}
