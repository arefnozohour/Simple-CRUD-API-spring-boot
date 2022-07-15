package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqUserData;
import com.semtrio.TestTask.domain.User;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.UserRepository;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.PostService;
import com.semtrio.TestTask.service.interfaces.TodoService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    ModelMapper modelMapperSkipNull;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl() {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqUserData.class, User.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqUserData.class, User.class);
    }
    @Override
    public User add(ReqUserData userData)
    {
        User user=modelMapper.map(userData,User.class);
        return userRepository.save(user);
    }

    @Override
    public User add(User userData) {
        return userRepository.save(userData);
    }

    @Override
    public User update(Integer userId, ReqUserData userData)
    {
        User user=this.getById(userId);
        modelMapper.getTypeMap(ReqUserData.class, User.class).setProvider(p -> user);
        User updatedUser=this.modelMapper.map(userData,User.class);
        return userRepository.save(updatedUser);
    }
    @Override
    public User patch(Integer userId, ReqUserData userData)
    {
        User user=this.getById(userId);
        modelMapperSkipNull.getTypeMap(ReqUserData.class, User.class).setProvider(p -> user);
        User updatedUser=this.modelMapperSkipNull.map(userData,User.class);
        return userRepository.save(updatedUser);
    }
    @Override
    public User getById(Integer userId)
    {
        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new ServiceException(ExceptionData.user_NOTFOUND);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Integer userId) {
        try {
            userRepository.deleteById(userId);
        }
        catch ( EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.user_NOTFOUND);
        }
    }

//    @Autowired
//    private TodoService todoService;
//
//    @Autowired
//    private AlbumService albumService;
//    @Autowired
//    private PostService postService;
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
