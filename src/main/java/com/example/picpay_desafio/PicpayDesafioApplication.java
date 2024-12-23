package com.example.picpay_desafio;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

// Por conta da data em Transaction entity
@EnableJdbcAuditing
@SpringBootApplication
public class PicpayDesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayDesafioApplication.class, args);
	}

	@Bean
	NewTopic notificationTopic() {
		return TopicBuilder.name("transaction-notification")
				.build();
	}

}
