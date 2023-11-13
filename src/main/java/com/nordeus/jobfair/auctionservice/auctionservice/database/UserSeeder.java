package com.nordeus.jobfair.auctionservice.auctionservice.database;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserSeeder {
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner seedUsers() {
        return args -> {
            if (this.userRepository.count() != 0) return;

            for (int i = 0; i < 10; i++) {
                this.userRepository.save(new User());
            }
            System.out.println(this.userRepository.findAll().get(0).getUserId().getValue());
        };
    }
}
