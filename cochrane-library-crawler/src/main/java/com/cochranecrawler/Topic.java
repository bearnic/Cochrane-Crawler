package com.cochranecrawler;

public class Topic {
    String topicName;
    String url;

    public Topic() {
    }

    public Topic(String topicName, String url) {
        this.topicName = topicName;
        this.url = url;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
