package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;

public class AuctionIsNotActiveException extends RuntimeException {
    public AuctionIsNotActiveException(AuctionId auctionId) {
        super("Auction: " + auctionId.getValue() + "is not active");
    }
}
