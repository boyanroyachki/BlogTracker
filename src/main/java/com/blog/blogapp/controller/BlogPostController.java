package com.blog.blogapp.controller;

import com.blog.blogapp.model.BlogPost;
import com.blog.blogapp.repository.BlogPostRepository;
import com.blog.blogapp.repository.SearchRepository;
import com.blog.blogapp.service.AuthUserDetailService;
import com.blog.blogapp.service.OllamaModelSercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogPostController {

    @Autowired
    private BlogPostRepository data;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private OllamaModelSercice ollamaModelSercice;

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @GetMapping("/posts")
    public List<BlogPost> getAll(){
        return data.findAll();
    }

    @GetMapping("/posts/{tag}")
    public List<BlogPost> getBlogPostByTag(@PathVariable String tag){
        return searchRepository.findByTag(tag);
    }

    @GetMapping("/posts/user/{username}")
    public List<BlogPost> getBlogPostsByUsername(@PathVariable String username){

        return searchRepository.findByUsername(username);
    }

    @PostMapping("/post")
    public BlogPost addBlogPost(@RequestBody BlogPost post)
    {
        post.setUsername(authUserDetailService.getCurrentUsername());
        return data.save(post);
    }



//    @GetMapping("/penis")
//    public String test() throws JsonProcessingException {
//        return ollamaModelSercice.generateResponse((Constants.PROMPT + "Who the fuck JFK?"), Constants.MODEL_NAME);
//    }
}
