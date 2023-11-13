package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionSchedulerServiceImpl implements AuctionSchedulerService {

    private final AuctionService auctionService;
    private final AuctionNotifer auctionNotifer;

    @Value("${auction.numberOfAuctionsCreated}")
    private int numberOfAuctionsCreated;

    @Override
    @Scheduled(fixedRate = 60000)
    public void createAuctions() {
        List<Auction> createdAuctions = new ArrayList<>();
        for (int i = 0; i < numberOfAuctionsCreated; i++) {
            Auction auction = this.auctionService.createAuction();
            createdAuctions.add(auction);
        }

        auctionNotifer.activeAuctionsRefreshed(createdAuctions);
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void checkAuctions() {
        Collection<Auction> auctions = this.auctionService.getAllActive();

        for (Auction auction : auctions) {
            if (LocalDateTime.now().isAfter(auction.getClosesAt())) {
                this.auctionService.closeAuction(auction.getAuctionId());
            }
        }

    }
}
