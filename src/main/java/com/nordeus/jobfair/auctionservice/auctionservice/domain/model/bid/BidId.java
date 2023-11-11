package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.bid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class BidId implements Serializable {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="bid_id")
    private UUID value;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BidId bidId = (BidId) o;
        return getValue() != null && Objects.equals(getValue(), bidId.getValue());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(value);
    }
}
