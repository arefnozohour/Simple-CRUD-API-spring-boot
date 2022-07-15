package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    User add(ReqUserData userData);
    @Transactional
    User add(User userData);
    @Transactional
    User update(Integer userId, ReqUserData userData);

    @Transactional
    User patch(Integer userId, ReqUserData userData);

    @Transactional(readOnly = true)
    User getById(Integer userId);
    @Transactional(readOnly = true)
    List<User> getAll();

    @Transactional
    void delete(Integer userId);
    @Transactional
    void deleteAll();
}
