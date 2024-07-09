package com.blog.blogapp.controller;


import com.blog.blogapp.model.BlogPost;
import com.blog.blogapp.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogPostController {

    @Autowired
    private BlogPostRepository data;

    @GetMapping("/posts")
    public List<BlogPost> getAll(){
        return data.findAll();
    }


    @GetMapping("posts/{tag}")
    public BlogPost getBlogPostByTag(@RequestBody String tag){

    }

    @PostMapping("/post")
    public BlogPost addBlogPost(@RequestBody BlogPost post)
    {
        return data.save(post);
    }
}
