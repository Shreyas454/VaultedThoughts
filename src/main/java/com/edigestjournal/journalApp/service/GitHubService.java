package com.edigestjournal.journalApp.service;

import com.edigestjournal.journalApp.api.response.GitHubResponse;
import com.edigestjournal.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GitHubService {


    private static final String API = "https://api.github.com/users/shreyas454";


    private final RestTemplate restTemplate = new RestTemplate();

    public GitHubResponse getInfo() {

        ResponseEntity<GitHubResponse> response = restTemplate.exchange(API, HttpMethod.GET, null, GitHubResponse.class);

        if(response != null){
            GitHubResponse body = response.getBody();
            return body;

        }
       else {
            return null;
        }

    }
}