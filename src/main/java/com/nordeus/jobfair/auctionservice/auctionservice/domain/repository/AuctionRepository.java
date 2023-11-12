package com.nordeus.jobfair.auctionservice.auctionservice.domain.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, AuctionId> {
    List<Auction> findByActiveTrue();

    Optional<Auction> findByAuctionIdAndActiveTrue(AuctionId auctionId);

}
