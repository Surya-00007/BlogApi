package com.blogapi.blog.controller;

import com.blogapi.blog.payload.CommentDto;
import com.blogapi.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/")
@RestController
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findCommentByPostId(@PathVariable(value = "postId") long postId){
        List<CommentDto> dto = commentService.findCommentByPostId(postId);
        return dto;
    }
    //http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long id){
        commentService.deleteCommentById(postId,id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long id){
     CommentDto dto=   commentService.getCommentById(postId,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping ("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id") long id,
                                                    @RequestBody CommentDto commentDto){
        CommentDto updateComment = commentService.updateComment(postId,id,commentDto);
        return new ResponseEntity<>(updateComment,HttpStatus.OK);

    }
}
