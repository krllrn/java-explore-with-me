package ru.practicum.ewm;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExploreWithMeServer {

	public static final String USER_ID_HEADER_REQUEST = "X-Sharer-User-Id";

	public static void main(String[] args) {
		SpringApplication.run(ExploreWithMeServer.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
