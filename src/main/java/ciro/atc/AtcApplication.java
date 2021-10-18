package ciro.atc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ciro.atc"})
public class AtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtcApplication.class, args);
	}

}
