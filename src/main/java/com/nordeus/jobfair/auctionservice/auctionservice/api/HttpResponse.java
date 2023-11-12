package com.nordeus.jobfair.auctionservice.auctionservice.api;

public record HttpResponse<E>(boolean success, E data, String message) {
}
