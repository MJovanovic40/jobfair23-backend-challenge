package com.nordeus.jobfair.auctionservice.auctionservice.domain.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.BidId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, BidId> {
    Optional<Bid> findByAuction_AuctionIdAndUser_UserId(AuctionId auctionId, UserId userId);


}
