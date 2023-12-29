package com.auction.entities;

import com.auction.entities.utils.StatesDbInsertion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class EntitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntitiesApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(StatesDbInsertion statesDbInsertion) {
		return args -> {
			statesDbInsertion.initializeOlxStates();
		};
	}
}
