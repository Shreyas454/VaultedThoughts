package com.edigestjournal.journalApp.service;

import com.edigestjournal.journalApp.api.response.WeatherResponse;
import com.edigestjournal.journalApp.cache.AppCache;
import com.edigestjournal.journalApp.entity.ConfigJournalApp;
import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.repository.Config_JournalAppRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component
public class WeatherService {
//    private static final String apiKey ="a2d6c811b83300c7af57a7928410cad3";

    @Value("${weather.api.key}")
    private   String apiKey ; // here we have used the value  annotation that injects the mentioned value from the application.properties file
                              // we also removed final as we are not declaring any = value here and also
                              // static declaration  was removed as any value declared that was is not touched by spring
                              // while creating the bean as a static value is connected to all the instances of a class and thus can affect all instances if changes

//    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AppCache appCache;

    @Autowired
    private Config_JournalAppRespository configJournalAppRespository;

    @Autowired
    private RedisService redisService;

//    final String API = appCache.App_Cache.get("weather_api"); older position

    public WeatherResponse getWeather(String city){

        WeatherResponse weatherResponse = redisService.get("weather_of"+city,WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }


        final String API = appCache.App_Cache.get("weather_api");

//        ConfigJournalApp call = configJournalAppRespository.findByKey("weather_api");  tried using a custom repo method ,works well
//        String API = call.getValue();                                                  data retrieved directly from repo instead of cache


        String url = API.replace("<API_KEY>", apiKey).replace("<CITY>", city);

        //if we are dealing with a post call where we will also be expected to send something we use the third field .ie requestEntity feild which holds Httpentity
//        HttpHeaders httpHeader = new HttpHeaders();
//        httpHeader.set("username","Shreyas");
//        httpHeader.set("password","Tirthraj");// a single instance of httpHeaders can hold multiple key/value pairs
//        httpHeader.remove("username");//this is how we can drop a keyvalue pair
//        User user = User.builder().username("Shreyas").password("Tirthraj").build();
//        HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeader);//thus we create a single entity that we can pass to the endpoint along with the request(POST)
//     //   ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, WeatherResponse.class);//sample command

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);

        WeatherResponse body =  response.getBody();
        if(body != null){
            redisService.set("weather_of_"+city,body,5l);
        }
        return body;

    }
}

//while using the API from AppCache earlier the position where api feild was being initiated was initiated right at time of bean creation
// at this stage , dependancy injection does not happen after the bean is created
// and thus API that was being created at the initialization time itself
// was turning out to be null when run at that position
// thus the main issue was that when API was created the appCache was null and thus could not retrive any data leading to Api being null