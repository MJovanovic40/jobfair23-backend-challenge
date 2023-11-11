package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/auctions")
public class HttpController {

    private AuctionService auctionService;

    @GetMapping("/active")
    public Collection<Auction> getAllActive() {
        return auctionService.getAllActive();
    }

    @GetMapping("/{auctionId}")
    public Auction getAuctionById(@PathVariable String auctionId) {
        return auctionService.getAuction(new AuctionId(UUID.fromString(auctionId)));
    }
}
