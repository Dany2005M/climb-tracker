package com.app.ClimbTracker.config;

import com.app.ClimbTracker.user.AppUser;
import com.app.ClimbTracker.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(userRepository.count() == 0){
                String encodedPassword = passwordEncoder.encode("database123");

                AppUser testUser = new AppUser("db_admin", encodedPassword, "ADMIN");
                userRepository.save(testUser);

                System.out.println("Test user 'db_admin' created successfully!");
            }
        };
    }

}
