package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {"com.system", "com.example"})
@EnableJpaRepositories(basePackages= {"com.example.demo.repository", "com.system.repository"})
@EntityScan(basePackages= {"com.example.demo.entity", "com.system.entity"})
public class SampleServerApplication {

	private static ConfigurableApplicationContext context = null;
	
	public static void main(String[] args) {
		context = SpringApplication.run(SampleServerApplication.class, args);
	}
	
	public static void close() {
		if(context != null) {
			context.close();
		}
	}

}
