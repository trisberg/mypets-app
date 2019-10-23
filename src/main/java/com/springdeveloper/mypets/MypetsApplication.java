package com.springdeveloper.mypets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class MypetsApplication {

	@Value("${spring.data.mongodb.database:}")
	String database;

	// @Autowired
    // private RestartEndpoint restartEndpoint;

	public static void main(String[] args) {
		SpringApplication.run(MypetsApplication.class, args);
	}

}
