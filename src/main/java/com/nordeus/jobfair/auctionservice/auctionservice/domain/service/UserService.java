package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;

public interface UserService {
    User getUserById(UserId userId);

    void addTokens(UserId userId, int tokens);

    void removeTokens(UserId userId, int tokens);
}
