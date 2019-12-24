package com.vicente.algamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.vicente.algamoney.api.config.property.AlgamoneyApiProterty;

@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyApiProterty.class)
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamoneyApiApplication.class, args);
	}

}
