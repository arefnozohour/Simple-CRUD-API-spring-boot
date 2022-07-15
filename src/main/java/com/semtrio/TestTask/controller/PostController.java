package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqPostData;
import com.semtrio.TestTask.data.response.ResCommentData;
import com.semtrio.TestTask.data.response.ResPostData;
import com.semtrio.TestTask.domain.Post;
import com.semtrio.TestTask.service.interfaces.CommentService;
import com.semtrio.TestTask.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<ResPostData> addPost(@RequestBody ReqPostData postData)
    {
        return ResponseEntity.ok(postService.makeResponse(postService.add(postData)));
    }
    @PutMapping("/{postId}")
    public ResponseEntity<ResPostData> updatePost(@PathVariable Integer postId, @Valid @RequestBody ReqPostData postData)
    {
        return ResponseEntity.ok(postService.makeResponse(postService.update(postId,postData)));
    }
    @PatchMapping("/{postId}")
    public ResponseEntity<ResPostData> patchPost(@PathVariable Integer postId, @RequestBody ReqPostData postData)
    {
        return ResponseEntity.ok(postService.makeResponse(postService.patch(postId,postData)));
    }
    @GetMapping("/{postId}")
    public ResponseEntity<ResPostData> getPost(@PathVariable Integer postId)
    {
        return ResponseEntity.ok(postService.makeResponse(postService.getById(postId)));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Integer postId)
    {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<ResPostData>> getAllPost(@RequestParam(required = false)Integer userId)
    {
        return ResponseEntity.ok(postService.makeResponse(postService.getAll(userId)));
    }
    @Autowired
    private CommentService commentService;


    @GetMapping("{postId}/comments")
    public ResponseEntity<List<ResCommentData>> getPostComments(@PathVariable Integer postId)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.getAll(postId)));
    }
}
