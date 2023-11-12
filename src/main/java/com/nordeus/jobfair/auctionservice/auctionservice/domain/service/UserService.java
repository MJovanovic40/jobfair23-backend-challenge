package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;

public interface UserService {
    User getUserById(UserId userId);
    void addTokens(User user, int tokens);
    void removeTokens(User user, int tokens);
}
