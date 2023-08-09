package com.cochranecrawler;

public class Review {
    String url;
    String topic;
    String title;
    String author;
    String date;


    public Review() {
    }

    public Review(String url, String topic, String title, String author, String date) {
        this.url = url;
        this.topic = topic;
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
