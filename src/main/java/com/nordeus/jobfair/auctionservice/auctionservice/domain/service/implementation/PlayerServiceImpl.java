package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.PlayerRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player getRandomPlayer() {
        List<Player> players = this.playerRepository.findAll();

        if (players.isEmpty()) {
            Player player = new Player();
            this.playerRepository.save(player);
            return player;
        }
        Random rand = new Random();
        return players.get(rand.nextInt(0, players.size()));
    }
}
