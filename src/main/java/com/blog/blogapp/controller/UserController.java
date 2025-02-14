package com.blog.blogapp.controller;

import com.blog.blogapp.model.AuthUser;
import com.blog.blogapp.model.BlogPost;
import com.blog.blogapp.model.LoginRequest;
import com.blog.blogapp.repository.AuthUserRepository;
import com.blog.blogapp.repository.BlogPostRepository;
import com.blog.blogapp.repository.SearchRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private final AuthUserRepository authUserRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BlogPostRepository data;



    //Register a new USER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthUser user){
        try {
            if (authUserRepository.findByUsername(user.getUsername()).isPresent())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken. Please try again");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            AuthUser save = authUserRepository.save(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    //Log in a USER and expect 200 OK to start Basic auth
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Logged in.");
    }


    //get USER by POST's ID
    @GetMapping("/post/{id}/user")
    public ResponseEntity<?> getUserByPostId(@PathVariable String id) {
        Optional<BlogPost> post = data.findById(id);

        if (post.isPresent()) {
            String username = post.get().getUsername();

            Optional<AuthUser> user = authUserRepository.findByUsername(username);
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for username: " + username);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
    }

}
