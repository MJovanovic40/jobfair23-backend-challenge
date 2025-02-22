package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;

import java.util.Collection;

public interface AuctionService {

    Collection<Auction> getAllActive();

    Auction getAuction(AuctionId auctionId);

    Auction getActiveAuction(AuctionId auctionId);

    void join(Auction auctionId, User user);

    void bid(AuctionId auctionId, UserId userId);

    Auction createAuction();

    void closeAuction(AuctionId auctionId);
}
