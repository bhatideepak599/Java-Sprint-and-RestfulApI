package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.BlogDto;
import com.techlabs.app.entity.Blog;

import jakarta.validation.Valid;

public interface BlogService {

	List<BlogDto> getAllBlogs();
	
	BlogDto fromBlogToBlogDto(Blog b);
	
	BlogDto saveBlog( BlogDto blogDto);

	BlogDto updateBlog( BlogDto blogDto);

	BlogDto getBlogBYId(int id);

	String deleteblog(int id);

	BlogDto assignCommentToABlog(int cId, int bId);

	BlogDto removeCommentToABlog(int cId, int bId);

}
