package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/auctions")
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

    @PostMapping(value = "/{auctionId}/bid")
    public HttpResponse<Object> bid(@PathVariable String auctionId, HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString(); // From jwt filter
        this.auctionService.bid(new AuctionId(UUID.fromString(auctionId)), new UserId(UUID.fromString(userId)));
        return new HttpResponse<>(true, null, "Bid successful.");
    }
}
