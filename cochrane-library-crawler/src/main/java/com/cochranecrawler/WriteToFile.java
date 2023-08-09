package com.cochranecrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {

  public void writeReviewsToFile(List<Review> reviews, FileWriter fileToWriteTo) throws IOException{
    for(Review review: reviews){
      StringBuilder sb = new StringBuilder();
      sb.append(review.getUrl()).append("|");
      sb.append(review.getTopic()).append("|");
      sb.append(review.getTitle()).append("|");
      sb.append(review.getAuthor()).append("|");
      sb.append(review.getDate());
      fileToWriteTo.write(sb.toString());
      fileToWriteTo.write(System.getProperty( "line.separator" ));
    }
  }
}
