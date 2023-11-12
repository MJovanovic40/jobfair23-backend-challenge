package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.PlayerRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    void setup() {
        playerService = new PlayerServiceImpl(playerRepository);
    }

    @Nested
    class GetRandomPlayerTests {
        @Test
        void playersDoNotExist() {
            when(playerRepository.findAll()).thenReturn(new ArrayList<>());

            Player player = playerService.getRandomPlayer();

            verify(playerRepository).findAll();
            verify(playerRepository).save(eq(player));
            assertInstanceOf(Player.class, player);
        }

        @Test
        void playersExist() {
            Player player = new Player();
            when(playerRepository.findAll()).thenReturn(List.of(player));

            Player returnedPlayer = playerService.getRandomPlayer();

            verify(playerRepository).findAll();
            assertEquals(returnedPlayer, player);
        }
    }
}