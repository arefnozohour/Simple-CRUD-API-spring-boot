package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.config.CustomUserDetailsService;
import com.semtrio.TestTask.config.JwtUtil;
import com.semtrio.TestTask.data.request.ReqLoginData;
import com.semtrio.TestTask.data.response.ResLoginData;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public ResLoginData login(ReqLoginData loginData) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new ServiceException(ExceptionData.user_NOTFOUND);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginData.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new ResLoginData(jwt);

    }
}
