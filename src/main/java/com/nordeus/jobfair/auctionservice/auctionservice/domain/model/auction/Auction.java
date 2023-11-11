package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="auctions")
public class Auction {
    @EmbeddedId
    private AuctionId auctionId;

    @Column(name="bid_price", nullable = false)
    private int bidPrice;

    @Column(name="closes_at", nullable = false)
    private LocalDateTime closesAt;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumns({
            @JoinColumn(name = "PLAYER_PLAYER_ID", referencedColumnName = "PLAYER_ID", nullable = false)
    })
    private Player player;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Auction auction = (Auction) o;
        return getAuctionId() != null && Objects.equals(getAuctionId(), auction.getAuctionId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(auctionId);
    }
}
