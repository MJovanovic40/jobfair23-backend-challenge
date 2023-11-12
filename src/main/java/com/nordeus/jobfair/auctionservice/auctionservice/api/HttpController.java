package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/auctions")
public class HttpController {

    private AuctionService auctionService;

    @GetMapping("/active")
    public HttpResponse<Collection<Auction>> getAllActive() {
        return new HttpResponse<>(true, auctionService.getAllActive(), "Auctions fetched successfully.");
    }

    @GetMapping("/{auctionId}")
    public HttpResponse<Auction> getAuctionById(@PathVariable String auctionId) {
        return new HttpResponse<>(true, auctionService.getAuction(new AuctionId(UUID.fromString(auctionId))), "Auction fetched successfully.");
    }

    @PostMapping("/{auctionId}/bid")
    public HttpResponse<Object> bid(@PathVariable String auctionId, @RequestBody String userId) {
        return new HttpResponse<>(true, null, "Bid successful.");
    }
}
