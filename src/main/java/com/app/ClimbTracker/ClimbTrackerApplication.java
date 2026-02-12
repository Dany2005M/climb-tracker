package com.app.ClimbTracker;

import com.app.ClimbTracker.climb.Climb;
import com.app.ClimbTracker.climb.ClimbRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ClimbTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimbTrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ClimbRepository climbRepository){
		return args -> {
			Climb c1 = new Climb("V4", "Overhang", true, LocalDate.now());
			Climb c2 = new Climb("5a", "Slab", false, LocalDate.now());

			climbRepository.save(c1);
			climbRepository.save(c2);

			System.out.println("Climbs inserted into the database!");
		};


	}

}
