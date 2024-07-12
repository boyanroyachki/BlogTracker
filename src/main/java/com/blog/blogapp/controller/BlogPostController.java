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
@CrossOrigin(origins = "http://localhost:3000") //react frontend
public class BlogPostController {

    @Autowired
    private BlogPostRepository data;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private OllamaModelSercice ollamaModelSercice;

    @Autowired
    private AuthUserDetailService authUserDetailService;



    //Return all POSTS
    @GetMapping("/posts")
    public List<BlogPost> getAll(){
        return data.findAll();
    }


    //Return all POSTS that contain a given TAG
    @GetMapping("/posts/{tag}")
    public List<BlogPost> getBlogPostByTag(@PathVariable String tag){
        return searchRepository.findByTag(tag);
    }


    //Get all POSTS that have a given USERNAME
    @GetMapping("/posts/user/{username}")
    public List<BlogPost> getBlogPostsByUsername(@PathVariable String username){
        return searchRepository.findByUsername(username);
    }


    //Add a new POST to the collection
    @PostMapping("/post")
    public BlogPost addBlogPost(@RequestBody BlogPost post)
    {
        post.setUsername(authUserDetailService.getCurrentUsername());
        post.setAuthUserId(authUserDetailService.getCurrentAuthUser().getId());
        return data.save(post);
    }

//    @GetMapping("/penis")
//    public String test() throws JsonProcessingException {
//        return ollamaModelSercice.generateResponse((Constants.PROMPT + "Who the fuck JFK?"), Constants.MODEL_NAME);
//    }
}
