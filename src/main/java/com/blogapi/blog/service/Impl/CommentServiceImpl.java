package com.blogapi.blog.service.Impl;

import com.blogapi.blog.entity.Comment;
import com.blogapi.blog.entity.Post;
import com.blogapi.blog.exceptions.ResourceNotFoundException;
import com.blogapi.blog.payload.CommentDto;
import com.blogapi.blog.repository.CommentRepository;
import com.blogapi.blog.repository.PostRepository;
import com.blogapi.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);

        CommentDto dto=new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());

        return dto;


    }

    @Override
    public List<CommentDto> findCommentByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId)

        );
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId)

        );
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        commentRepo.deleteById(id);

    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId)

        );
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        CommentDto dto = mapToDto(comment);
        return dto;
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId)

        );
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comment);
        CommentDto dto = mapToDto(updatedComment);

        return dto;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

}
