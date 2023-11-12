package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.BidRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.PlayerService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.UserService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.AuctionIsNotActiveException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.BidDoesNotExistException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidAuctionIdException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.UserAlreadyInAuctionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository; // BidRepository is included here because it is a part of the auction service, and doesn't require a service of its own

    private final UserService userService;
    private final AuctionNotifer auctionNotifer;
    private final PlayerService playerService;

    @Value("${auction.length}")
    private int auctionLength;

    @Value("${auction.initialBidPrice}")
    private int initialBidPrice;

    /**
     * Returns all active auctions
     *
     * @return A list of Auction objects
     */
    @Override
    public Collection<Auction> getAllActive() {
        return this.auctionRepository.findByActiveTrue();
    }

    /**
     * Returns an Auction object based on the provided AuctionId
     *
     * @param auctionId the AuctionId object containing the id of the auction
     * @return the Auction object with the requested id
     * @throws InvalidAuctionIdException if the auction doesn't exist with the provided id
     */
    @Override
    public Auction getAuction(AuctionId auctionId) {
        return this.auctionRepository.findById(auctionId).orElseThrow(() -> new InvalidAuctionIdException(auctionId));
    }

    /**
     * Returns the auction with the provided AuctionId object, only if it's active
     *
     * @param auctionId id of the auction
     * @return Auction object of the active auction
     */
    @Override
    public Auction getActiveAuction(AuctionId auctionId) {
        return this.auctionRepository.findByAuctionIdAndActiveTrue(auctionId).orElseThrow(() -> new AuctionIsNotActiveException(auctionId));
    }

    /**
     * Adds the user to the auction
     *
     * @param auction Auction object of the auction that users wants to join
     * @param user    User object that joins the auction
     */
    @Override
    public void join(Auction auction, User user) {
        if (auction.getUsers().contains(user))
            throw new UserAlreadyInAuctionException(user, auction); // Prevent the user from joining the auction more than once
        auction.getUsers().add(user);
        this.auctionRepository.save(auction);
    }

    /**
     * Places a bid on an auction
     * If the user isn't a part of the auction yet, they join the auction and place a new bid.
     * Otherwise, the already placed bid is modified
     *
     * @param auctionId AuctionId object of the target auction
     * @param userId    UserId object of the user who placed the bid
     */
    @Override
    public void bid(AuctionId auctionId, UserId userId) {
        Auction auction = this.getActiveAuction(auctionId);
        User user = this.userService.getUserById(userId);

        int bidPrice = auction.getBidPrice();
        this.userService.removeTokens(userId, bidPrice);

        Bid bid;
        if (auction.getUsers().contains(user)) { // User has joined the auction already, so just modify bid
            bid = this.bidRepository.findByAuction_AuctionIdAndUser_UserId(auctionId, userId).orElseThrow(() -> new BidDoesNotExistException(auctionId, userId));

            bid.setAmount(bidPrice);

            auction.getBids().remove(bid);
            auction.getBids().add(bid);
        } else { // User hasn't joined the auction yet, so join auction and create bid
            this.join(auction, user);

            bid = new Bid();
            bid.setAuction(auction);
            bid.setUser(user);
            bid.setAmount(bidPrice);
        }

        LocalDateTime presentTime = LocalDateTime.now();
        LocalDateTime closingTime = auction.getClosesAt();

        //If player makes a bid at the last 5 seconds, extend the auction to 5 seconds
        if (presentTime.isBefore(closingTime) && presentTime.plusSeconds(5).isAfter(closingTime)) {
            auction.setClosesAt(presentTime.plusSeconds(5));
        }

        auction.setBidPrice(auction.getBidPrice() + 1);

        this.bidRepository.save(bid);
        this.auctionRepository.save(auction);

        auctionNotifer.bidPlaced(bid);
    }

    /**
     * Creates a new Auction.
     */
    @Override
    public void createAuction() {
        Player auctionedPlayer = this.playerService.getRandomPlayer(); // This is supposed to simulate a service call to get the actual player that is supposed to be on auction

        Auction auction = new Auction();
        auction.setClosesAt(LocalDateTime.now().plusMinutes(auctionLength));
        auction.setBidPrice(initialBidPrice);
        auction.setActive(true);
        auction.setPlayer(auctionedPlayer);

        this.auctionRepository.save(auction);
    }

    /**
     * Closes the Auction with the provided AuctionId and notifies the winner
     *
     * @param auctionId AuctionId object of the auction to close
     */
    @Override
    public void closeAuction(AuctionId auctionId) {
        Auction auction = this.getActiveAuction(auctionId);

        auction.setActive(false);

        this.auctionRepository.save(auction);

        this.auctionNotifer.auctionFinished(auction);
    }
}
