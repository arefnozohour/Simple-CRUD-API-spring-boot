package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqCommentData;
import com.semtrio.TestTask.data.response.ResCommentData;
import com.semtrio.TestTask.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseEntity<ResCommentData> addComment(@Valid @RequestBody ReqCommentData commentData)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.add(commentData)));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<ResCommentData> updateComment(@PathVariable Integer commentId, @Valid @RequestBody ReqCommentData commentData)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.update(commentId,commentData)));
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<ResCommentData> patchComment(@PathVariable Integer commentId, @RequestBody ReqCommentData commentData)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.patch(commentId,commentData)));
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<ResCommentData> getComment(@PathVariable Integer commentId)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.getById(commentId)));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer commentId)
    {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<ResCommentData>> getAllComment(@RequestParam(required = false)Integer postId)
    {
        return ResponseEntity.ok(commentService.makeResponse(commentService.getAll(postId)));
    }
}
