package com.interview.copilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.interview.copilot"})
public class CopilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopilotApplication.class, args);

	}

}
