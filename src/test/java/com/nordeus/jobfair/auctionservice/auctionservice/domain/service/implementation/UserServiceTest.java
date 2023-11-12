package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.implementation;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.UserRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.UserService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InsufficientTokensException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidTokenAmountException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidUserIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setup() {
        this.userService = new UserServiceImpl(userRepository);
    }

    @Nested
    class GetUserTests {
        @Test
        void happyPath() {
            User user = new User();
            when(userRepository.findById(any(UserId.class))).thenReturn(Optional.of(user));

            User expectedUser = userService.getUserById(user.getUserId());
            assertEquals(user, expectedUser);
            verify(userRepository).findById(eq(user.getUserId()));
        }

        @Test
        void invalidUserId() {
            when(userRepository.findById(any(UserId.class))).thenThrow(InvalidUserIdException.class);
            assertThrows(InvalidUserIdException.class, () -> userService.getUserById(new User().getUserId()));
            verify(userRepository).findById(any(UserId.class));
        }
    }

    @Nested
    class AddTokensTests {
        @Test
        void happyPath() {
            int tokens = 5;
            User user = new User();

            userService.addTokens(user.getUserId(), tokens);

            verify(userRepository).save(user);
        }

        @Test
        void invalidTokenAmount() {
            int tokens = -5;
            User user = new User();

            assertThrows(InvalidTokenAmountException.class, () -> userService.addTokens(user.getUserId(), tokens));
        }

        @Test
        void invalidUserId() {
            int tokens = 5;
            when(userRepository.findById(any(UserId.class))).thenThrow(InvalidUserIdException.class);

            User user = new User();

            assertThrows(InvalidUserIdException.class, () -> userService.addTokens(user.getUserId(), tokens));
            verify(userRepository).findById(eq(user.getUserId()));
        }
    }

    @Nested
    class RemoveTokensTests {
        @Test
        void happyPath() {
            int tokens = 5;
            User user = new User();

            userService.removeTokens(user.getUserId(), tokens);

            verify(userRepository).save(user);
        }

        @Test
        void invalidTokenAmount() {
            int tokens = -5;
            User user = new User();

            assertThrows(InvalidTokenAmountException.class, () -> userService.removeTokens(user.getUserId(), tokens));
        }

        @Test
        void insufficientTokens() {
            int tokens = 1000;
            User user = new User();

            assertThrows(InsufficientTokensException.class, () -> userService.removeTokens(user.getUserId(), tokens));
        }

        @Test
        void invalidUserId() {
            int tokens = 5;
            when(userRepository.findById(any(UserId.class))).thenThrow(InvalidUserIdException.class);

            User user = new User();

            assertThrows(InvalidUserIdException.class, () -> userService.removeTokens(user.getUserId(), tokens));
            verify(userRepository).findById(eq(user.getUserId()));
        }
    }
}