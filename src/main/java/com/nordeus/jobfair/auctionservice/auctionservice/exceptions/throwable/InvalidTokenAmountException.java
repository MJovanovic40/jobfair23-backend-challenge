package com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable;

public class InvalidTokenAmountException extends RuntimeException {
    public InvalidTokenAmountException(int amount) {
        super("Invalid token amount: " + amount);
    }
}
