package com.blogapi.blog.repository;

import com.blogapi.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post,Long> {


}

