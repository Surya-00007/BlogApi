package com.blogapi.blog.service;

import com.blogapi.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);


    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);
}
