package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqLoginData;
import com.semtrio.TestTask.data.response.ResLoginData;
import com.semtrio.TestTask.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<ResLoginData> login(@Valid @RequestBody ReqLoginData loginData)
    {
        return ResponseEntity.ok(authService.login(loginData));
    }
}
