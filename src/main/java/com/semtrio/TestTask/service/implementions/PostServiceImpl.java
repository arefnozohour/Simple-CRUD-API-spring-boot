package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.response.ResPostData;
import com.semtrio.TestTask.domain.Post;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.PostRepository;
import com.semtrio.TestTask.service.interfaces.CommentService;
import com.semtrio.TestTask.service.interfaces.PostService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    ModelMapper modelMapperSkipNull;
    ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl() {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqPostData.class, Post.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqPostData.class, Post.class).addMappings(mapper -> mapper.skip(Post::setId));;
        modelMapper.createTypeMap(Post.class, ResPostData.class).addMappings(
                mapper -> mapper.map(src -> src.getUser().getId(), ResPostData::setUserId)
        );
    }
    @Override
    public Post add(ReqPostData postData)
    {
        Post post=modelMapper.map(postData,Post.class);
        post.setId(null);
        post.setUser(userService.getById(postData.getUserId()));
        return postRepository.save(post);
    }
    @Override
    public Post update(Integer postId, ReqPostData postData)
    {
        Post post=this.getById(postId);
        modelMapper.getTypeMap(ReqPostData.class, Post.class).setProvider(p -> post);
        Post updatedPost=this.modelMapper.map(postData,Post.class);
        return postRepository.save(updatedPost);
    }
    @Override
    public Post patch(Integer postId,ReqPostData postData)
    {
        Post post=this.getById(postId);
        modelMapperSkipNull.getTypeMap(ReqPostData.class, Post.class).setProvider(p -> post);
        Post updatedPost=this.modelMapperSkipNull.map(postData,Post.class);
        return postRepository.save(updatedPost);
    }
    @Override
    public List<Post> getAll(Integer userId)
    {
        if (userId==null)
            return postRepository.findAll();
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public Post getById(Integer postId)
    {
        Optional<Post> post=postRepository.findById(postId);
        if (post.isPresent())
            return post.get();
        throw new ServiceException(ExceptionData.post_NOTFOUND);
    }

    @Override
    public void delete(Integer postId)
    {
        try {
            postRepository.deleteById(postId);
        }
        catch ( EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.post_NOTFOUND);
        }
    }
//
//    @Autowired
//    CommentService commentService;
    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }

    @Override
    public ResPostData makeResponse(Post post)
    {
        return modelMapper.map(post,ResPostData.class);
    }

    @Override
    public List<ResPostData> makeResponse(List<Post> posts)
    {
        return posts.stream()
                .map(element -> modelMapper.map(element, ResPostData.class))
                .collect(Collectors.toList());
    }

}
