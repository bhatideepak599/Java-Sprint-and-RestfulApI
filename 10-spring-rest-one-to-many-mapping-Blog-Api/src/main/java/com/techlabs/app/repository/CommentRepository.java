package com.techlabs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findCommentByBlog_id(int blog_id);

}
