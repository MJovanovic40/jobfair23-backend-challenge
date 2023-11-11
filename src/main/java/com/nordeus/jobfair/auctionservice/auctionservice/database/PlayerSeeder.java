package com.nordeus.jobfair.auctionservice.auctionservice.database;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.PlayerId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PlayerSeeder {

    private final PlayerRepository playerRepository;

    @Bean
    CommandLineRunner seedPlayers() {
        return args -> {
            if(this.playerRepository.count() != 0) return;

            for(int i = 0; i < 50; i++) {
                this.playerRepository.save(new Player());
            }
        };
    }
}
