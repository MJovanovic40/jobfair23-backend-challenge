package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;

import java.util.Collection;

public interface AuctionNotifer {

    void auctionFinished(Auction auction);

    void bidPlaced(Bid bid);

    void activeAuctionsRefreshed(Collection<Auction> activeAuctions);
}
