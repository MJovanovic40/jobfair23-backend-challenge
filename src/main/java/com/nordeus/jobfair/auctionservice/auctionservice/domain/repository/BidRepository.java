package com.nordeus.jobfair.auctionservice.auctionservice.domain.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.BidId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, BidId> {
}
