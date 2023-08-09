package com.cochranecrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetReviews {
    
    List<Review> reviews;
    String nextUrl;

    public GetReviews() {
    }

    public GetReviews(List<Review> reviews, String nextUrl) {
        this.reviews = reviews;
        this.nextUrl = nextUrl;
    }

    public GetReviews getReviewsFromTopic(Topic topic, CloseableHttpClient httpClient) throws IOException{

        String topicName = topic.getTopicName();
        String topicUrl = topic.getUrl();
        GetContent getContent = new GetContent().getContentFromUrl(topicUrl,httpClient);
        String topicContent = getContent.getPageContentOutput();
        Document topicDoc = Jsoup.parse(topicContent);
        Elements resultsElements = topicDoc.getElementsByClass("search-results-item-body");
        
        List<Review> reviewsFromTopic = new ArrayList<>();
        for (Element resultElement : resultsElements) {
            Element titleElement = resultElement.getElementsByClass("result-title").first();
            String reviewTitle = titleElement.text();
            String reviewUrl = titleElement.select("h3 > a").first().attr("href");
            String reviewAuthor = resultElement.getElementsByClass("search-result-authors").first().text();
            String reviewDate = resultElement.getElementsByClass("search-result-date").first().text();
            
            Review review = new Review();
            review.setTitle(reviewTitle);
            review.setUrl("https://www.cochranelibrary.com"+reviewUrl);
            review.setTopic(topicName);
            review.setDate(reviewDate);
            review.setAuthor(reviewAuthor);
            reviewsFromTopic.add(review);
        }


        Element nextButton = topicDoc.getElementsByClass("pagination-next-link").first();
        String nextButtonUrl = null;
        if(nextButton!=null && nextButton.select("div > a")!=null && nextButton.select("div > a").first() !=null){
            nextButtonUrl = nextButton.select("div > a").first().attr("href");
        }

        if(nextButtonUrl != null){
            topic.url = nextButtonUrl;
            GetReviews getReviews = new GetReviews().getReviewsFromTopic(topic,httpClient);
            reviewsFromTopic.addAll(getReviews.getReviews());
        }
        
        return new GetReviews(reviewsFromTopic, nextButtonUrl);
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getNextUrl() {
        return this.nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
