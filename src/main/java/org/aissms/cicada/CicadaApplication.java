package org.aissms.cicada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class CicadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CicadaApplication.class, args);
	}
	@GetMapping("/page")
	public String getPage() {
		return "home";
	}
}
