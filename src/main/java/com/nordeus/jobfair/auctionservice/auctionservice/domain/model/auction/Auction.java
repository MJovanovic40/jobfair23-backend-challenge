package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auctions")
public class Auction {
    @EmbeddedId
    private AuctionId auctionId = new AuctionId();

    @Column(name = "bid_price", nullable = false)
    private int bidPrice;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "closes_at", nullable = false)
    private LocalDateTime closesAt;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "users_in_auctions",
            joinColumns = @JoinColumn(name = "auction_auction_id"),
            inverseJoinColumns = @JoinColumn(name = "users_user_id"))
    private Set<User> users = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumns({
            @JoinColumn(name = "PLAYER_PLAYER_ID", referencedColumnName = "PLAYER_ID", nullable = false)
    })
    private Player player;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
