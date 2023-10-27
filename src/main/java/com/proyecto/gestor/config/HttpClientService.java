package com.proyecto.gestor.config;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class HttpClientService {

    private final HttpClient httpClient = HttpClients.createDefault();

    public String sendGetRequest(String url) throws IOException{
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);

        try{
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }finally {
            response.close();
        }
    }

}
