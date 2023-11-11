package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionNotifer auctionNotifer;
    private final AuctionRepository auctionRepository;

    @Override
    /**
     * A method that returns all active auctions.
     * @Returns A list of Auction objects.
     */
    public Collection<Auction> getAllActive() {
        return this.auctionRepository.findByActiveTrue();
    }

    @Override
    public Auction getAuction(AuctionId auctionId) {
        return null;
    }

    @Override
    public void join(AuctionId auctionId, User user) {

    }

    @Override
    public void bid(AuctionId auctionId, UserId userId) {
        auctionNotifer.bidPlaced(new Bid());
    }
}
