package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.UserRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.UserService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InsufficientTokensException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidTokenAmountException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Returns the User object with the provided id
     *
     * @param userId UserId object
     * @return User object with the provided id
     */
    @Override
    public User getUserById(UserId userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));
    }

    /**
     * Adds the provided amount of tokens to the User object
     *
     * @param userId User object where to add the tokens
     * @param tokens amount of tokens to add
     * @throws InvalidTokenAmountException if token amount is less than 0
     */
    @Override
    public void addTokens(UserId userId, int tokens) {
        if (tokens < 0) {
            throw new InvalidTokenAmountException(tokens);
        }

        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));

        user.setTokens(user.getTokens() + tokens);
        this.userRepository.save(user);
    }

    /**
     * Subtracts the user's tokens by the provided amount.
     *
     * @param userId User object from whom to subtract the tokens
     * @param tokens amount of tokens to subtract
     * @throws InvalidTokenAmountException if token amount is less than 0
     * @throws InsufficientTokensException if the user doesn't have enough tokens for the transaction
     */
    @Override
    public void removeTokens(UserId userId, int tokens) {
        if (tokens < 0) {
            throw new InvalidTokenAmountException(tokens);
        }

        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));

        if (tokens > user.getTokens()) {
            throw new InsufficientTokensException(user, tokens);
        }

        user.setTokens(user.getTokens() - tokens);
        this.userRepository.save(user);
    }
}
