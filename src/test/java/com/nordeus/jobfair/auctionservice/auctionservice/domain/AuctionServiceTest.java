package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.BidRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.PlayerService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.UserService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.AuctionIsNotActiveException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidAuctionIdException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.UserAlreadyInAuctionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuctionServiceTest {

    @Mock
    private AuctionNotifer auctionNotifer;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private UserService userService;

    @Mock
    private PlayerService playerService;

    private AuctionService auctionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        auctionService = spy(new AuctionServiceImpl(auctionRepository, bidRepository, userService, auctionNotifer, playerService));
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

            auctionService.join(auction, user);

            verify(auctionRepository).save(auction);
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

            assertThrows(UserAlreadyInAuctionException.class, () -> auctionService.join(auction, user));
        }
    }

    @Nested
    class GetActiveAuctionTests {
        @Test
        void happyPath() {
            Auction auction = new Auction();
            when(auctionRepository.findByAuctionIdAndActiveTrue(any(AuctionId.class))).thenReturn(Optional.of(auction));

            Auction expectedAuction = auctionService.getActiveAuction(auction.getAuctionId());

            assertEquals(expectedAuction, auction);
            verify(auctionRepository).findByAuctionIdAndActiveTrue(eq(auction.getAuctionId()));

        }

        @Test
        void invalidAuctionIdOrInvalidAuctionId() {
            AuctionId auctionId = new AuctionId();

            when(auctionRepository.findByAuctionIdAndActiveTrue(any(AuctionId.class))).thenThrow(AuctionIsNotActiveException.class);

            assertThrows(AuctionIsNotActiveException.class, () -> auctionService.getActiveAuction(auctionId));
            verify(auctionRepository).findByAuctionIdAndActiveTrue(eq(auctionId));

        }
    }

    @Nested
    class BidTests {

        @Test
        void userInAuction() {
            int bidPrice = 2;

            User user = new User();

            Auction auction = new Auction();
            auction.setActive(true);
            auction.setClosesAt(LocalDateTime.now().plusSeconds(5));
            auction.setBidPrice(bidPrice);

            Bid bid = new Bid();
            bid.setAmount(bidPrice - 1);
            bid.setUser(user);

            auction.getBids().add(bid);
            auction.getUsers().add(user);
            bid.setAuction(auction);

            doReturn(auction).when(auctionService).getActiveAuction(any(AuctionId.class));
            when(userService.getUserById(any(UserId.class))).thenReturn(user);
            when(bidRepository.findByAuction_AuctionIdAndUser_UserId(any(AuctionId.class), any(UserId.class))).thenReturn(Optional.of(bid));

            auctionService.bid(auction.getAuctionId(), user.getUserId());

            verify(auctionService).getActiveAuction(eq(auction.getAuctionId()));
            verify(userService).getUserById(eq(user.getUserId()));
            verify(userService).removeTokens(eq(user.getUserId()), eq(bidPrice));
            verify(auctionNotifer).bidPlaced(eq(bid));
            verify(bidRepository).save(eq(bid));
            verify(auctionRepository).save(eq(auction));

            verify(bidRepository).findByAuction_AuctionIdAndUser_UserId(eq(auction.getAuctionId()), eq(user.getUserId()));

        }

        @Test
        void userNotInAuction() {
            int bidPrice = 1;

            User user = new User();

            Auction auction = new Auction();
            auction.setActive(true);
            auction.setClosesAt(LocalDateTime.now().plusSeconds(5));
            auction.setBidPrice(bidPrice);

            doReturn(auction).when(auctionService).getActiveAuction(any(AuctionId.class));
            when(userService.getUserById(any(UserId.class))).thenReturn(user);

            auctionService.bid(auction.getAuctionId(), user.getUserId());

            verify(auctionService).getActiveAuction(eq(auction.getAuctionId()));
            verify(userService).getUserById(eq(user.getUserId()));
            verify(userService).removeTokens(eq(user.getUserId()), eq(bidPrice));
            verify(auctionService).join(eq(auction), eq(user));
            verify(auctionNotifer).bidPlaced(any(Bid.class));
            verify(bidRepository).save(any(Bid.class));
            verify(auctionRepository, times(2)).save(eq(auction));

        }

    }

    @Nested
    class CreateAuctionTests {
        @Test
        void happyPath() {
            when(playerService.getRandomPlayer()).thenReturn(new Player());

            auctionService.createAuction();

            verify(playerService).getRandomPlayer();
            verify(auctionRepository).save(any(Auction.class));
        }
    }

    @Nested
    class CloseAuctionTest {
        @Test
        void happyPath() {
            Auction auction = new Auction();
            auction.setActive(true);
            auction.setClosesAt(LocalDateTime.now());
            auction.setPlayer(new Player());

            doReturn(auction).when(auctionService).getActiveAuction(any(AuctionId.class));

            auctionService.closeAuction(auction.getAuctionId());

            verify(auctionService).getActiveAuction(any(AuctionId.class));
            verify(auctionRepository).save(eq(auction));
            verify(auctionNotifer).auctionFinished(eq(auction));
        }
    }
}