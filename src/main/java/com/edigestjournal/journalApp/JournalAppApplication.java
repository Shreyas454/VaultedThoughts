package com.edigestjournal.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling //enables executing of @Scheduled methods
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}

	@Bean //return actual implementation of interface instance
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

//	@Bean
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}
}
