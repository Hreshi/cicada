package org.aissms.cicada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
@CrossOrigin
public class CicadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CicadaApplication.class, args);
	}
	@GetMapping("/page")
	public String getPage() {
		return "home";
	}
	@GetMapping("/ping")
	public ResponseEntity<String> isAuthorized() {
		return ResponseEntity.ok("");
	}
}
