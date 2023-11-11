package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionNotifer auctionNotifer;

    @Override
    public Collection<Auction> getAllActive() {
        return new LinkedList<>();
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
