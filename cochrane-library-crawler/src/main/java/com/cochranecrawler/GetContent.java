package com.cochranecrawler;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class GetContent {

    String pageContentOutput;
    CloseableHttpClient httpClientOutput;

    public GetContent(String pageContentOutput, CloseableHttpClient httpClientOutput) {
        this.pageContentOutput = pageContentOutput;
        this.httpClientOutput = httpClientOutput;
    }

    public GetContent() {
    }
    
    public GetContent getContentFromUrl(String url, CloseableHttpClient httpClient) throws IOException
    {
        if(httpClient == null){
            List<BasicHeader> defaultHeaders = Arrays.asList(new BasicHeader("X-Default-Header", "default header httpclient"));
            RequestConfig config = RequestConfig.custom()
            .setCircularRedirectsAllowed(true).build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal(50);
            httpClient = HttpClients.custom()
            .setDefaultRequestConfig(config)
            .setDefaultHeaders(defaultHeaders)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
            .setConnectionManager(connectionManager)
            .build();
        }

        try {
            HttpUriRequest request = RequestBuilder.get()
            .setUri(url)
            .build();

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            return new GetContent(httpClient.execute(request, responseHandler),httpClient);
            
        } catch (Exception e) {
            e.printStackTrace();
            httpClient.close();
            return new GetContent("",null);
        }
    }

    public String getPageContentOutput() {
        return this.pageContentOutput;
    }

    public void setPageContentOutput(String pageContentOutput) {
        this.pageContentOutput = pageContentOutput;
    }

    public CloseableHttpClient getHttpClientOutput() {
        return this.httpClientOutput;
    }

    public void setHttpClientOutput(CloseableHttpClient httpClientOutput) {
        this.httpClientOutput = httpClientOutput;
    }
}
