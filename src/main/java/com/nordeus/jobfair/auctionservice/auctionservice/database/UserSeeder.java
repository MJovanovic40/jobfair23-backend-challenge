package com.nordeus.jobfair.auctionservice.auctionservice.database;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.UserRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserSeeder {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils; // For simulation purposes

    @Bean
    CommandLineRunner seedUsers() {
        return args -> {
            if (this.userRepository.count() != 0) return;

            for (int i = 0; i < 10; i++) {
                User user = new User();
                this.userRepository.save(user);
                System.out.println(jwtUtils.createUserToken(user.getUserId()));
            }
        };
    }
}
