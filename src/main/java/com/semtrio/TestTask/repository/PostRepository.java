package com.semtrio.TestTask.repository;

import com.semtrio.TestTask.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByUserId(Integer userId);
}
