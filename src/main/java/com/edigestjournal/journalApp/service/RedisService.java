package com.edigestjournal.journalApp.service;

import com.edigestjournal.journalApp.api.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.runtime.ObjectMethods;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate ;

    public <T> T get(String key,Class<T> entityClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass); // here the Jackson Object mapper reeconstructs the Json String in the mentioned WeatherResponse class     }catch (Exception e){

        } catch (Exception e) {
            return null;
        }
    }


    //aletrnate method that specifically works for weather response
    public WeatherResponse get(String key){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Object JsonResponse = redisTemplate.opsForValue().get(key);
            return mapper.readValue(JsonResponse.toString(),WeatherResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

//    Even though you're using StringRedisSerializer, Spring's RedisTemplate is designed to handle different data types flexibly.
//    When you store a value in Redis using RedisTemplate, it serializes the data into a byte[] format to store it in Redis.
//    However, when you retrieve that data, RedisTemplate doesn't assume it's always a String.
//    It returns the data as a generic Object type, which allows flexibility in case you use custom serializers or store different types of objects.
    public void set(String key,Object o,Long ttl){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o); // can map any object like User,Journal etc to a String of Json Format(this is deserializing step)
            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.MINUTES);//(key,value(string),expireation time,unitof time of expiring)

        }catch (Exception e){
//            return null;
        }
    }

}
//Json is a data format String is a data type
// Object mapper of Jackson can map any object type to Json String format


// The ObjectMapper's readValue() method is versatile and can accept different types of input
// for deserialization. It can handle:
// 1. String: Typically used when the input is a JSON string representation, which is converted
//    into a specified Java object (e.g., WeatherResponse.class).
// 2. InputStream: Can be used to deserialize JSON data from a file or network stream.
// 3. byte[]: If the input JSON data is in the form of a byte array, it can also be processed.
// 4. Reader: A Reader (e.g., BufferedReader or FileReader) can also be passed if the data comes
//    from a character-based input stream.
// For example, you would use it like this:
// return mapper.readValue(JsonResponse, WeatherResponse.class);
