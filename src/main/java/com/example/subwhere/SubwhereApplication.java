package com.example.subwhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class SubwhereApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SubwhereApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
