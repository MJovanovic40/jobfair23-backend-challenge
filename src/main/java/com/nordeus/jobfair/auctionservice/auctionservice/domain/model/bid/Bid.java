package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="bids")
public class Bid implements Serializable {

    @EmbeddedId
    private BidId bidId;

    @Column(name="amount", nullable = false)
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "AUCTION_AUCTION_ID", referencedColumnName = "AUCTION_ID")
    })
    private Auction auction;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumns({
            @JoinColumn(name = "USER_USER_ID", referencedColumnName = "USER_ID", nullable = false)
    })
    private User user;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Bid bid = (Bid) o;
        return getBidId() != null && Objects.equals(getBidId(), bid.getBidId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(bidId);
    }
}
