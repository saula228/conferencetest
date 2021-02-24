package ru.kulikov.saula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kulikov.saula.config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class SaulaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaulaApplication.class, args);
	}

}
