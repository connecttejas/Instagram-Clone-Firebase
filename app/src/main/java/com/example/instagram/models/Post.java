package com.example.instagram.models;

public class Post {

    String description;
    String imageurl;
    String postid;
    String publisher;


    Post(){}


    public Post(String description, String imageUrl, String postId, String publisher) {
        this.description = description;
        this.imageurl = imageUrl;
        this.postid = postId;
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageurl = imageUrl;
    }

    public String getPostId() {
        return postid;
    }

    public void setPostId(String postId) {
        this.postid = postId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
