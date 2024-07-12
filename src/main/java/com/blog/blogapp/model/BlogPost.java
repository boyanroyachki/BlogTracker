package com.blog.blogapp.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "BlogPost")
public class BlogPost {

    @Id
    private String id;

    private String topic;

    private String content;

    private String username;

    @DBRef
    private AuthUser author;

    private String[] tags;
}
