package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techlabs.app.dto.CommentDto;
import com.techlabs.app.entity.Blog;
import com.techlabs.app.entity.Comment;
import com.techlabs.app.exceptions.NotFoundException;
import com.techlabs.app.repository.BlogRepository;
import com.techlabs.app.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	private BlogRepository blogRepository;
	private CommentRepository commentRepository;
	private BlogServiceImpl blogServiceImpl;

	public CommentServiceImpl(BlogRepository blogRepository, CommentRepository commentRepository) {
		super();
		this.blogRepository = blogRepository;
		this.commentRepository = commentRepository;
		// this.blogServiceImpl=blogServiceImpl;
	}

	@Override
	public Comment commentDtoToComment(CommentDto commentDto, Blog blog) {
		Comment c = new Comment();
		c.setId(commentDto.getId());
		c.setDescription(commentDto.getDescription());
		c.setBlog(blog);

		return c;
	}

	@Override
	public CommentDto commentToCommentDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setDescription(comment.getDescription());
		commentDto.setId(comment.getId());
		return commentDto;
	}

	@Override
	public CommentDto saveComment(CommentDto commentDto) {
		Comment c = new Comment();
		c.setDescription(commentDto.getDescription());
		commentRepository.save(c);
		return commentToCommentDto(c);
	}

	@Override
	public List<CommentDto> getAllComments() {
		List<Comment> all = commentRepository.findAll();

		List<CommentDto> comments = getList(all);

		return comments;
	}

	@Override
	public List<CommentDto> getCommentByBlogId(int id) {
		List<Comment> all = commentRepository.findCommentByBlog_id(id);
		return getList(all);

	}

	private List<CommentDto> getList(List<Comment> all) {
		if (all == null || all.size() == 0)
			throw new NotFoundException("No Comments Found");
		List<CommentDto> comments = new ArrayList<>();
		for (Comment c : all) {
			comments.add(commentToCommentDto(c));
		}
		return comments;
	}

}
