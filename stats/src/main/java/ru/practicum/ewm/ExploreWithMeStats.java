package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExploreWithMeStats {
	public static final String USER_ID_HEADER_REQUEST = "X-Sharer-User-Id";

	public static void main(String[] args) {
		SpringApplication.run(ExploreWithMeStats.class, args);
	}

}
