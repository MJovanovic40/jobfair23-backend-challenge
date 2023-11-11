package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player;


import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction.Auction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="players")
public class Player {
    @EmbeddedId
    private PlayerId playerId = new PlayerId();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Auction> auctions = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Player player = (Player) o;
        return getPlayerId() != null && Objects.equals(getPlayerId(), player.getPlayerId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(playerId);
    }
}
