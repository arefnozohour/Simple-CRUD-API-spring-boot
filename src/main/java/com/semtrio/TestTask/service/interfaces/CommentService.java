package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqCommentData;
import com.semtrio.TestTask.data.response.ResCommentData;
import com.semtrio.TestTask.domain.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    @Transactional
    Comment add(ReqCommentData commentData);
    @Transactional(readOnly = true)
    Comment getById(Integer commentId);
    @Transactional
    Comment update(Integer commentId, ReqCommentData commentData);
    @Transactional
    Comment patch(Integer commentId, ReqCommentData commentData);
    @Transactional
    void delete(Integer commentId);
    @Transactional
    void deleteAll();
    @Transactional(readOnly = true)
    List<Comment> getAll(Integer postId);

    ResCommentData makeResponse(Comment comment);

    List<ResCommentData> makeResponse(List<Comment> comments);
}
