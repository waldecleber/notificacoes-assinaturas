package com.waldecleber.globoplay.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EntityScan("com.waldecleber.globoplay.subscription.model")
@EnableJpaRepositories("com.waldecleber.globoplay.subscription.repository")
@ComponentScan("com.waldecleber.globoplay")
@EnableWebSocket
@EnableWebSocketMessageBroker
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
