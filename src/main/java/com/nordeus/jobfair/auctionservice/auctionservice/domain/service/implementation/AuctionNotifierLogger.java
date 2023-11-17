package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class AuctionNotifierLogger implements AuctionNotifer {

    @Override
    public void auctionFinished(Auction auction) {
        String winnerMessage = "";

        if (auction.getBids().isEmpty()) {
            winnerMessage = "There were no bids.";
        } else {
            List<Bid> bids = auction.getBids();
            bids.sort(new Comparator<Bid>() {
                @Override
                public int compare(Bid o1, Bid o2) {
                    return o1.getAmount() - o2.getAmount();
                }
            });
            Bid winningBid = bids.get(bids.size() - 1);
            winnerMessage = "The winner is: " + winningBid.getUser().getUserId().getValue().toString() + " who bid " + winningBid.getAmount() + " token(s).";
        }

        log.info("Auction with id: " + auction.getAuctionId().getValue().toString() + " finished. " + winnerMessage);
    }

    @Override
    public void bidPlaced(Bid bid) {
        log.info("Bid placed by user: " + bid.getUser().getUserId().getValue().toString() + ". Amount: " + bid.getAmount() + " token(s).");
    }

    @Override
    public void activeAuctionsRefreshed(Collection<Auction> activeAuctions) {
        log.info("Active auctions are refreshed. New auctions: {}", activeAuctions);
    }
}
