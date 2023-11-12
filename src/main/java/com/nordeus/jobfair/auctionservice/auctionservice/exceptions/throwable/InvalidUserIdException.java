package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(UserId userId) {
        super("User not found with id: " + userId.getValue());
    }
}
