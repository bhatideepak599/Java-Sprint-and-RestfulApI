package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.techlabs.app.dto.BlogDto;
import com.techlabs.app.entity.Blog;
import com.techlabs.app.entity.Comment;
import com.techlabs.app.exceptions.NotFoundException;
import com.techlabs.app.repository.BlogRepository;
import com.techlabs.app.repository.CommentRepository;

@Service
public class BlogServiceImpl implements BlogService {
	private final BlogRepository blogRepository;
	private final CommentRepository commentRepository;
	private final CommentService commentService;

	public BlogServiceImpl(BlogRepository blogRepository, CommentRepository commentRepository,
			CommentService commentService) {
		this.blogRepository = blogRepository;
		this.commentRepository = commentRepository;
		this.commentService = commentService;
	}

	@Override
	public List<BlogDto> getAllBlogs() {
		List<Blog> blogs = blogRepository.findAll();
		if (blogs.isEmpty()) {
			throw new NotFoundException("No Blog Found");
		}
		return getBlogsDtoList(blogs);
	}

	@Override
	public BlogDto saveBlog(BlogDto blogDto) {
		blogDto.setId(0);
		Blog blog = fromBlogDtoToBlog(blogDto);
		Blog savedBlog = blogRepository.save(blog);
		return fromBlogToBlogDto(savedBlog);
	}

	@Override
	public BlogDto updateBlog(BlogDto blogDto) {
		Optional<Blog> byId = blogRepository.findById(blogDto.getId());
		if (byId.isEmpty())
			throw new NotFoundException("No Blog Found with Id : " + blogDto.getId());

		Blog blog = fromBlogDtoToBlog(blogDto);
		Blog savedBlog = blogRepository.save(blog);
		return fromBlogToBlogDto(savedBlog);

	}

	@Override
	public BlogDto getBlogBYId(int id) {
		Optional<Blog> byId = blogRepository.findById(id);
		if (byId.isEmpty())
			throw new NotFoundException("No Blog Found with Id : " + id);

		return fromBlogToBlogDto(byId.get());
	}

	@Override
	public String deleteblog(int id) {
		Optional<Blog> byId = blogRepository.findById(id);
		if (byId.isEmpty())
			throw new NotFoundException("No Blog Found with Id : " + id);
		Blog blog = byId.get();
		for (Comment c : blog.getComments()) {
			c.setBlog(null);
			commentRepository.save(c);
		}
		blog.setComments(null);
		blogRepository.delete(byId.get());
		return "Blog With Id :" + id + " Deleted SuccessFully";
	}

	@Override
	public BlogDto assignCommentToABlog(int cId, int bId) {
		Optional<Blog> byId = blogRepository.findById(bId);
		if (byId.isEmpty())
			throw new NotFoundException("No Blog Found with Id : " + bId);

		Optional<Comment> byId2 = commentRepository.findById(cId);
		if (byId2.isEmpty())
			throw new NotFoundException("No Comment Found with Id : " + cId);
		Blog blog = byId.get();
		Comment comment = byId2.get();

		comment.setBlog(blog);
		commentRepository.save(comment);

		blog.getComments().add(comment);
		blogRepository.save(blog);

		BlogDto fromBlogToBlogDto = fromBlogToBlogDto(blog);

		return fromBlogToBlogDto;
	}

	@Override
	public BlogDto removeCommentToABlog(int cId, int bId) {

		Optional<Blog> byId = blogRepository.findById(bId);
		if (byId.isEmpty())
			throw new NotFoundException("No Blog Found with Id : " + bId);

		Optional<Comment> byId2 = commentRepository.findById(cId);
		if (byId2.isEmpty())
			throw new NotFoundException("No Comment Found with Id : " + cId);
		Blog blog = byId.get();
		Comment comment = byId2.get();

		comment.setBlog(null);
		commentRepository.save(comment);

		blog.getComments().remove(comment);
		blogRepository.save(blog);

		BlogDto fromBlogToBlogDto = fromBlogToBlogDto(blog);

		return fromBlogToBlogDto;
	}
	@Override
	public BlogDto fromBlogToBlogDto(Blog b) {
		BlogDto dto = new BlogDto();
		dto.setId(b.getId());
		dto.setCategory(b.getCategory());
		dto.setData(b.getData());
		dto.setDate(b.getDate());
		dto.setTitle(b.getTitle());
		dto.setPublished(b.isPublished());
		if (b.getComments() != null) {
			dto.setCommentsDto(b.getComments().stream().map(commentService::commentToCommentDto).toList());
		}
		return dto;
	}

	private Blog fromBlogDtoToBlog(BlogDto b) {
		Blog blog = new Blog();
		blog.setId(b.getId());
		blog.setCategory(b.getCategory());
		blog.setData(b.getData());
		blog.setDate(new Date()); //
		blog.setTitle(b.getTitle());
		blog.setPublished(b.isPublished());

		if (b.getCommentsDto() != null) {
			blog.setComments(b.getCommentsDto().stream()
					.map(commentDto -> commentService.commentDtoToComment(commentDto, blog)).toList());
		}
		return blog;
	}
	

	private List<BlogDto> getBlogsDtoList(List<Blog> blogs) {
		List<BlogDto> blogsDtos = new ArrayList<>();
		for (Blog b : blogs) {
			blogsDtos.add(fromBlogToBlogDto(b));
		}
		return blogsDtos;
	}

}
