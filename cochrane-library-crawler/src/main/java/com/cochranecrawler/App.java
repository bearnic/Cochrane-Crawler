package com.cochranecrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App 
{
    //args[0] = filepath and filename
    public static void main( String[] args ) throws IOException
    {
        System.out.println("Starting Cochrane Crawler");
        System.out.println("__________________________________________________________________________________________________________________________");
        GetContent responseFromCochrane = new GetContent();
        responseFromCochrane = responseFromCochrane.getContentFromUrl("https://www.cochranelibrary.com/cdsr/reviews/topics", null);
        Document cochraneTopicsPage = Jsoup.parse(responseFromCochrane.getPageContentOutput());
        CloseableHttpClient persistentHttpClient = responseFromCochrane.getHttpClientOutput();
        System.out.println("Getting list of topics");
        System.out.println("__________________________________________________________________________________________________________________________");
        Elements topicElements = cochraneTopicsPage.getElementsByClass("browse-by-list-item");
        List<Topic> topics = new ArrayList<>();
        for (Element topicElement : topicElements) {
            String urlFromTopic = topicElement.select("li > a").first().attr("href");
            String topicName = topicElement.text();
            System.out.println(topicName);
            Topic topic = new Topic(topicName,urlFromTopic);
            topics.add(topic);
        }
        HashSet<String> topicInputNames = new HashSet<>();
        Scanner topicInput = new Scanner(System.in);
        String topicInputName = "";
        while(!topicInputName.equals("ALL") && !topicInputName.equals("STOP")){
            System.out.println("__________________________________________________________________________________________________________________________");
            System.out.println("Enter a topic to get the reviews for from the above list of provided topics. If you want all the topics enter the word ALL");
            System.out.println("if you are done entering topics enter STOP");
            topicInputName = topicInput.nextLine();
            topicInputNames.add(topicInputName);
        }
        System.out.println("__________________________________________________________________________________________________________________________");
        String filePath = "cochrane_reviews.txt";
        if(args.length > 0){
            filePath = args[0];
        }
        System.out.println("Getting reviews for topic and writing to file");
        System.out.println("__________________________________________________________________________________________________________________________");
        FileWriter fileWriter = new FileWriter(filePath);
        for(Topic currentTopic: topics){
            if(topicInputNames.contains("ALL") || topicInputNames.contains(currentTopic.topicName)){
                GetReviews getReview = new GetReviews();
                getReview = getReview.getReviewsFromTopic(currentTopic,persistentHttpClient);
                new WriteToFile().writeReviewsToFile(getReview.getReviews(), fileWriter);
            }
        }
        fileWriter.close();
        persistentHttpClient.close();
        topicInput.close();
        System.out.println("Cochrane Crawler is finished");
    }
}
