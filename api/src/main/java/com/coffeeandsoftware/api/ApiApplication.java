package com.coffeeandsoftware.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

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
