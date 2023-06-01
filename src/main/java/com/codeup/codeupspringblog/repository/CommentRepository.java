package com.codeup.codeupspringblog.repository;

import com.codeup.codeupspringblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostId(long id);
}
