package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidAuctionIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuctionServiceTest {

    @Mock
    private AuctionNotifer auctionNotifer;

    @Mock
    private AuctionRepository auctionRepository;

    private AuctionService auctionService;

    @BeforeEach
    void setup() {
        this.auctionService = new AuctionServiceImpl(auctionNotifer, auctionRepository);
    }

    @Nested
    class GetAllActiveTests {
        @Test
        void happyPath() {
            when(auctionRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

            Collection<Auction> auctions = auctionService.getAllActive();

            verify(auctionRepository).findByActiveTrue();
            assertEquals(auctions, new ArrayList<>());
        }
    }

    @Nested
    class GetAuctionByIdTests {
        @Test
        void happyPath() {
            Auction expectedAuction = new Auction();
            expectedAuction.setActive(true);
            expectedAuction.setPlayer(new Player());
            expectedAuction.setClosesAt(LocalDateTime.now().plusMinutes(1));
            when(auctionRepository.findById(any(AuctionId.class))).thenReturn(Optional.of(expectedAuction));

            Auction auction = auctionService.getAuction(expectedAuction.getAuctionId());
            assertEquals(expectedAuction, auction);
            verify(auctionRepository).findById(eq(expectedAuction.getAuctionId()));
        }

        @Test
        void invalidId() {
            when(auctionRepository.findById(any(AuctionId.class))).thenThrow(InvalidAuctionIdException.class);

            assertThrows(InvalidAuctionIdException.class, () -> auctionService.getAuction(new AuctionId()));
            verify(auctionRepository).findById(any(AuctionId.class));
        }
    }

    @Nested
    class JoinAuctionTests {
        @Test
        void happyPath() {
            User user = new User();
            Player player = new Player();

            Auction auction = new Auction();
            auction.setActive(true);
            auction.setClosesAt(LocalDateTime.now().plusMinutes(1));
            auction.setPlayer(player);

            AuctionId auctionId = auction.getAuctionId();

            when(auctionRepository.findById(any(AuctionId.class))).thenReturn(Optional.of(auction));

            auctionService.join(auctionId, user);

            verify(auctionRepository).findById(auctionId);
            verify(auctionRepository).save(auction);
        }

        @Test
        void invalidAuctionIdTest() {
            when(auctionRepository.findById(any(AuctionId.class))).thenThrow(InvalidAuctionIdException.class);
            assertThrows(InvalidAuctionIdException.class, () -> auctionService.join(new AuctionId(), new User()));
        }

        @Test
        void userAlreadyInAuctionTest() {
            User user = new User();
            Player player = new Player();

            Auction auction = new Auction();
            auction.setActive(true);
            auction.setClosesAt(LocalDateTime.now().plusMinutes(1));
            auction.setPlayer(player);
            auction.getUsers().add(user);

            AuctionId auctionId = auction.getAuctionId();

            when(auctionRepository.findById(any(AuctionId.class))).thenReturn(Optional.of(auction));

            auctionService.join(auctionId, user);

            verify(auctionRepository).findById(auctionId);
        }
    }

}