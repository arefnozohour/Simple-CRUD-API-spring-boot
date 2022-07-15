package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqCommentData;
import com.semtrio.TestTask.data.response.ResCommentData;
import com.semtrio.TestTask.domain.Comment;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.CommentRepository;
import com.semtrio.TestTask.service.interfaces.CommentService;
import com.semtrio.TestTask.service.interfaces.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    ModelMapper modelMapper;
    ModelMapper modelMapperSkipNull;

    @Autowired
    public CommentServiceImpl()
    {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqCommentData.class, Comment.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqCommentData.class, Comment.class).addMappings(mapper -> mapper.skip(Comment::setId));;
        modelMapper.createTypeMap(Comment.class, ResCommentData.class).addMappings(
                mapper -> mapper.map(src -> src.getPost().getId(), ResCommentData::setPostId)
        );
    }

    @Override
    public Comment add(ReqCommentData commentData)
    {
        Comment comment=modelMapper.map(commentData,Comment.class);
        comment.setPost(postService.getById(commentData.getPostId()));
        return commentRepository.save(comment);
    }

    @Override
    public Comment getById(Integer commentId)
    {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if (comment.isPresent())
            return comment.get();
        throw new ServiceException(ExceptionData.comment_NOTFOUND);
    }

    @Override
    public Comment update(Integer commentId, ReqCommentData commentData)
    {
        Comment comment=this.getById(commentId);
        modelMapper.getTypeMap(ReqCommentData.class, Comment.class).setProvider(p->comment);
        Comment updatedComment=modelMapper.map(commentData,Comment.class);
        return commentRepository.save(updatedComment);
    }

    @Override
    public Comment patch(Integer commentId, ReqCommentData commentData)
    {
        Comment comment=this.getById(commentId);
        modelMapperSkipNull.getTypeMap(ReqCommentData.class, Comment.class).setProvider(p->comment);
        Comment updatedComment=modelMapperSkipNull.map(commentData,Comment.class);
        return commentRepository.save(updatedComment);
    }
    @Override
    public void delete(Integer commentId)
    {
        try {
            commentRepository.deleteById(commentId);
        }catch (EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.comment_NOTFOUND);
        }
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }

    @Override
    public List<Comment> getAll(Integer postId)
    {
        if (postId==null)
            return commentRepository.findAll();
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public ResCommentData makeResponse(Comment comment)
    {
        return modelMapper.map(comment,ResCommentData.class);
    }

    @Override
    public List<ResCommentData> makeResponse(List<Comment> comments)
    {
        return comments.stream()
                .map(element -> modelMapper.map(element, ResCommentData.class))
                .collect(Collectors.toList());
    }
}
