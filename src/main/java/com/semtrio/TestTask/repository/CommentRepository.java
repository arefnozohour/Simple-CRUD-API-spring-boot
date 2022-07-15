package com.semtrio.TestTask.repository;

import com.semtrio.TestTask.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByPostId(Integer postId);
}
