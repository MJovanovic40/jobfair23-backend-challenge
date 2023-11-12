package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;

public class InsufficientTokensException extends RuntimeException {
    public InsufficientTokensException(User user, int tokens) {
        super("The user with id: " + user.getUserId().getValue() + " has insufficient tokens for the transaction of " + tokens + " tokens.");
    }
}
