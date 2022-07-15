package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqTodoData;
import com.semtrio.TestTask.data.response.ResTodoData;
import com.semtrio.TestTask.service.interfaces.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping
    public ResponseEntity<ResTodoData> addTodo(@RequestBody ReqTodoData todoData)
    {
        return ResponseEntity.ok(todoService.makeResponse(todoService.add(todoData)));
    }
    @PutMapping("/{todoId}")
    public ResponseEntity<ResTodoData> updateTodo(@PathVariable Integer todoId, @Valid @RequestBody ReqTodoData todoData)
    {
        return ResponseEntity.ok(todoService.makeResponse(todoService.update(todoId,todoData)));
    }
    @PatchMapping("/{todoId}")
    public ResponseEntity<ResTodoData> patchTodo(@PathVariable Integer todoId, @RequestBody ReqTodoData todoData)
    {
        return ResponseEntity.ok(todoService.makeResponse(todoService.patch(todoId,todoData)));
    }
    @GetMapping("/{todoId}")
    public ResponseEntity<ResTodoData> getTodo(@PathVariable Integer todoId)
    {
        return ResponseEntity.ok(todoService.makeResponse(todoService.getById(todoId)));
    }
    @DeleteMapping("/{todoId}")
    public ResponseEntity deleteTodo(@PathVariable Integer todoId)
    {
        todoService.delete(todoId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<ResTodoData>> getAllTodo(@RequestParam(required = false)Integer userId)
    {
        return ResponseEntity.ok(todoService.makeResponse(todoService.getAll(userId)));
    }


}
