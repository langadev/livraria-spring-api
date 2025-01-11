package com.apiDelivros.livrariaLanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LivrariaLangaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaLangaApplication.class, args);
	}

}


