package com.edigestjournal.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){ // spring autoinjects the arguments/parameters if any overhere when annoted with @Bean
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // store/read clean plain text keys
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;

    }
//    The significance of using the RedisConnectionFactory is that it reads the Redis details
//    from application.properties and integrates them with the RedisTemplate bean
//    , allowing the template to connect to the correct Redis server.

}
