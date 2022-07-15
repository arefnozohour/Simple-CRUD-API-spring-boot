package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.response.ResPostData;
import com.semtrio.TestTask.domain.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {
    @Transactional
    Post add(ReqPostData postData);
    @Transactional
    Post update(Integer postId, ReqPostData postData);
    @Transactional
    Post patch(Integer postId, ReqPostData postData);
    @Transactional(readOnly = true)
    List<Post> getAll(Integer userId);
    @Transactional(readOnly = true)
    Post getById(Integer postId);
    @Transactional
    void delete(Integer postId);
    @Transactional
    void deleteAll();


    ResPostData makeResponse(Post post);

    List<ResPostData> makeResponse(List<Post> posts);
}
