package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.throwable.InvalidAuctionIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionNotifer auctionNotifer;
    private final AuctionRepository auctionRepository;

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
     */
    @Override
    public Auction getAuction(AuctionId auctionId) {
        return this.auctionRepository.findById(auctionId).orElseThrow(() -> new InvalidAuctionIdException(auctionId));
    }

    /**
     * Adds the user to the auction
     *
     * @param auctionId AuctionId object of the auction that users wants to join
     * @param user      User object that joins the auction
     */
    @Override
    public void join(AuctionId auctionId, User user) {
        Auction auction = this.auctionRepository.findById(auctionId).orElseThrow(() -> new InvalidAuctionIdException(auctionId));
        if (auction.getUsers().contains(user)) return; // Prevent the user from joining the auction more than once
        auction.getUsers().add(user);
        this.auctionRepository.save(auction);
    }

    @Override
    public void bid(AuctionId auctionId, UserId userId) {
        auctionNotifer.bidPlaced(new Bid());
    }
}
