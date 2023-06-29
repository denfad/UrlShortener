package ru.denfad.UrlShortener;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class UrlShortenerApplication {

	@PostConstruct
	void init(){
		System.out.println(System.getProperty("PID"));
	}

	public static void main(String[] args) {

		SpringApplication.run(UrlShortenerApplication.class, args);

	}

}
