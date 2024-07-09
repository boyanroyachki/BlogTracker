package com.blog.blogapp.repository;

import com.blog.blogapp.model.BlogPost;

import java.util.List;

public interface SearchRepository {
    List<BlogPost> findByTag(String tag);
}
