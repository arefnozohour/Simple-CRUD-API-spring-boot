package com.semtrio.TestTask.repository;

import com.semtrio.TestTask.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findAllByUserId(Integer userId);
}
