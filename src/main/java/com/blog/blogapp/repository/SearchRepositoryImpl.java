package com.blog.blogapp.repository;

import com.blog.blogapp.model.BlogPost;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class SearchRepositoryImpl implements SearchRepository{

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;


    @Override
    public List<BlogPost> findByTag(String tag) {

        final List<BlogPost> posts = new ArrayList<>();

        MongoDatabase database = client.getDatabase("BlogTracker");
        MongoCollection<Document> collection = database.getCollection("BlogPost");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$search",
                new Document("index", "postIndex")
                .append("text",
                new Document("query", tag)
                .append("path", "tags")))));

        result.forEach(doc -> posts.add(converter.read(BlogPost.class,doc)));

        return posts;
    }

    @Override
    public List<BlogPost> findByUsername(String username){
        final List<BlogPost> posts = new ArrayList<>();

        MongoDatabase database = client.getDatabase("BlogTracker");
        MongoCollection<Document> collection = database.getCollection("BlogPost");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$search",
                new Document("index", "postIndex")
                .append("text",
                new Document("query", username)
                .append("path", "username")))));

        result.forEach(doc -> posts.add(converter.read(BlogPost.class,doc)));

        return posts;
    }
}
