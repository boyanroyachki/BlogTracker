package com.blog.blogapp.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "BlogPost")
public class BlogPost {

    private String topic;

    private String content;

    private String username;

    private String[] tags;

    public BlogPost() {
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
