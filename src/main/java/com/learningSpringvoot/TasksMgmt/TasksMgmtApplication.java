package com.learningSpringvoot.TasksMgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TasksMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksMgmtApplication.class, args);
	}

}
