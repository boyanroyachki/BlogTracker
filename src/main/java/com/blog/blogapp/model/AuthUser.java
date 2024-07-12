package com.blog.blogapp.model;


import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "User")
public class AuthUser {

    @Id
    private String id;

    @Indexed
    @Size(min = 3, max = 15, message = "Username must be between 3 and 20 characters")
    private String username;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    private boolean active;

}
