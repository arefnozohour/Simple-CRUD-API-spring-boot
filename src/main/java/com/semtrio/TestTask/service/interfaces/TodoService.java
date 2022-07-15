package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqTodoData;
import com.semtrio.TestTask.data.response.ResTodoData;
import com.semtrio.TestTask.domain.Todo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoService {

    @Transactional
    Todo add(ReqTodoData todoData);

    @Transactional
    Todo update(Integer todoId, ReqTodoData todoData);

    @Transactional
    Todo patch(Integer todoId, ReqTodoData todoData);

    @Transactional(readOnly = true)
    List<Todo> getAll(Integer userId);

    @Transactional(readOnly = true)
    Todo getById(Integer todoId);

    @Transactional
    void delete(Integer todoId);
    @Transactional
    void deleteAll();

    ResTodoData makeResponse(Todo todo);

    List<ResTodoData> makeResponse(List<Todo> todos);

}
