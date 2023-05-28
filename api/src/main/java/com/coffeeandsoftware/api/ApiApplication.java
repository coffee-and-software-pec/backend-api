package com.coffeeandsoftware.api;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeeandsoftware.controller.PublicationController;

@SuppressWarnings("unused")
@SpringBootApplication
@RestController
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
//		PublicationController controller = new PublicationController();
//		controller.savePublication("teste", LocalDateTime.now());
	}
}
