package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;

public class InvalidAuctionIdException extends RuntimeException {
    public InvalidAuctionIdException(AuctionId id) {
        super("Invalid Auction id: " + id.getValue());
    }
}
