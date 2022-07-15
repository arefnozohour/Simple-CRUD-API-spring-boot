package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody ReqUserData userData)
    {
        return ResponseEntity.ok(userService.add(userData));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId,@Valid @RequestBody ReqUserData userData)
    {
        return ResponseEntity.ok(userService.update(userId,userData));
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Integer userId, @RequestBody ReqUserData userData)
    {
        return ResponseEntity.ok(userService.patch(userId,userData));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId)
    {
        return ResponseEntity.ok(userService.getById(userId));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId)
    {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser()
    {
        return ResponseEntity.ok(userService.getAll());
    }
}
