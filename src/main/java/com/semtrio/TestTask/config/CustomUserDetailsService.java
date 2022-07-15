package com.semtrio.TestTask.config;

import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class CustomUserDetailsService  implements UserDetailsService {
    private final Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void initialize() {
        users.put("admin", new User("admin", "admin",Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        users.put("user", new User("user", "user", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username))
            throw new ServiceException(ExceptionData.user_NOTFOUND);
        return users.get(username);
    }
}
