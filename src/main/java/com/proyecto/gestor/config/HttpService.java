package com.proyecto.gestor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpService {
    private final HttpClientService httpClientService;

    @Autowired
    public HttpService(HttpClientService httpClientService){
        this.httpClientService = httpClientService;
    }

    public String fetchDataFromApi() throws IOException{
        String apiUrl = "https://api.github.com/users/andresgomezfrr";
        return httpClientService.sendGetRequest(apiUrl);
    }

}
