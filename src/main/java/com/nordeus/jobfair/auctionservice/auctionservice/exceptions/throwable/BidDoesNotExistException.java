package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;

public class BidDoesNotExistException extends RuntimeException {
    public BidDoesNotExistException(AuctionId auctionId, UserId userId) {
        super("Bid doesn't exist in auction: " + auctionId.getValue() + " by user: " + userId.getValue() + ".");
    }
}
