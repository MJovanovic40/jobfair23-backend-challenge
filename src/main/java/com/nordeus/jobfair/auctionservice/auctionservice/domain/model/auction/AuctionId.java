package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.auction;

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
@Embeddable
public class AuctionId implements Serializable {

    @Column(name="auction_id")
    private UUID value;

    public AuctionId() {
        this.value = UUID.randomUUID();
    }

    public AuctionId(UUID value) {
        this.value = value;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AuctionId auctionId = (AuctionId) o;
        return getValue() != null && Objects.equals(getValue(), auctionId.getValue());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(value);
    }
}