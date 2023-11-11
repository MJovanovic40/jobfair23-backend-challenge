package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
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


}