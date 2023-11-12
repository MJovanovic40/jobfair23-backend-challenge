package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;

public class UserAlreadyInAuctionException extends RuntimeException {
    public UserAlreadyInAuctionException(User user, Auction auction) {
        super("User: " + user.getUserId().getValue() + " already in auction: " + auction.getAuctionId().getValue() + ".");
    }
}
