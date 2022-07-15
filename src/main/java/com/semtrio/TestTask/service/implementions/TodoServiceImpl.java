package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqTodoData;
import com.semtrio.TestTask.data.response.ResTodoData;
import com.semtrio.TestTask.domain.Todo;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.TodoRepository;
import com.semtrio.TestTask.service.interfaces.TodoService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserService userService;
    ModelMapper modelMapperSkipNull;
    ModelMapper modelMapper;

    @Autowired
    public TodoServiceImpl() {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqTodoData.class, Todo.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqTodoData.class, Todo.class).addMappings(mapper -> mapper.skip(Todo::setId));;
        modelMapper.createTypeMap(Todo.class, ResTodoData.class).addMappings(
                mapper -> mapper.map(src -> src.getUser().getId(), ResTodoData::setUserId)
        );
    }
    @Override
    public Todo add(ReqTodoData todoData)
    {
        Todo todo=modelMapper.map(todoData,Todo.class);
        todo.setId(null);
        todo.setUser(userService.getById(todoData.getUserId()));
        return todoRepository.save(todo);
    }
    @Override
    public Todo update(Integer todoId, ReqTodoData todoData)
    {
        Todo todo=this.getById(todoId);
        modelMapper.getTypeMap(ReqTodoData.class, Todo.class).setProvider(p -> todo);
        Todo updatedTodo=this.modelMapper.map(todoData,Todo.class);
        return todoRepository.save(updatedTodo);
    }
    @Override
    public Todo patch(Integer todoId,ReqTodoData todoData)
    {
        Todo todo=this.getById(todoId);
        modelMapperSkipNull.getTypeMap(ReqTodoData.class, Todo.class).setProvider(p -> todo);
        Todo updatedTodo=this.modelMapperSkipNull.map(todoData,Todo.class);
        return todoRepository.save(updatedTodo);
    }
    @Override
    public List<Todo> getAll(Integer userId)
    {
        if (userId==null)
            return todoRepository.findAll();
        return todoRepository.findAllByUserId(userId);
    }

    @Override
    public Todo getById(Integer todoId)
    {
        Optional<Todo> todo=todoRepository.findById(todoId);
        if (todo.isPresent())
            return todo.get();
        throw new ServiceException(ExceptionData.todo_NOTFOUND);
    }

    @Override
    public void delete(Integer todoId)
    {
        try {
            todoRepository.deleteById(todoId);
        }
        catch ( EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.todo_NOTFOUND);
        }
    }

    @Override
    public void deleteAll() {
        todoRepository.deleteAll();
    }

    @Override
    public ResTodoData makeResponse(Todo todo)
    {
        return modelMapper.map(todo,ResTodoData.class);
    }

    @Override
    public List<ResTodoData> makeResponse(List<Todo> todos)
    {
        return todos.stream()
                .map(element -> modelMapper.map(element, ResTodoData.class))
                .collect(Collectors.toList());
    }
}
