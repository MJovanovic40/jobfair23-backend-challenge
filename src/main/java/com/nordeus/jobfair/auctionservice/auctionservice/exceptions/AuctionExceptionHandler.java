package com.nordeus.jobfair.auctionservice.auctionservice.exceptions;

import com.nordeus.jobfair.auctionservice.auctionservice.api.HttpResponse;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuctionExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> globalErrorHandler(Throwable e) {
        return new HttpResponse<>(false, null, "Something went wrong. Please try again.");
    }

    @ResponseBody
    @ExceptionHandler(InvalidAuctionIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> invalidAuctionIdHandler(InvalidAuctionIdException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> invalidUserIdHandler(InvalidUserIdException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidTokenAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> invalidTokenAmountHandler(InvalidTokenAmountException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InsufficientTokensException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public HttpResponse<Object> insufficientTokensHandler(InsufficientTokensException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BidDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> bidDoesNotExistHandler(BidDoesNotExistException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyInAuctionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> bidDoesNotExistHandler(UserAlreadyInAuctionException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AuctionIsNotActiveException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse<Object> auctionIsNotActiveHandler(AuctionIsNotActiveException e) {
        return new HttpResponse<>(false, null, e.getMessage());
    }

}
