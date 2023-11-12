package com.nordeus.jobfair.auctionservice.auctionservice.exceptions;

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
    public ErrorResponse globalErrorHandler(Throwable e) {
        return new ErrorResponse("Something went wrong. Please try again.");
    }

    @ResponseBody
    @ExceptionHandler(InvalidAuctionIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidAuctionIdHandler(InvalidAuctionIdException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidUserIdHandler(InvalidUserIdException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidTokenAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidTokenAmountHandler(InvalidTokenAmountException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InsufficientTokensException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse insufficientTokensHandler(InsufficientTokensException e) {
        return new ErrorResponse(e.getMessage());
    }

}
