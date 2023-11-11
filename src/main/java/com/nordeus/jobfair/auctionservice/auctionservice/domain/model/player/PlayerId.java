package com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player;

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
public class PlayerId implements Serializable {
    @Column(name="player_id")
    private UUID value;

    public PlayerId() {
        this.value = UUID.randomUUID();
    }

    public PlayerId(UUID value) {
        this.value = value;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PlayerId playerId = (PlayerId) o;
        return getValue() != null && Objects.equals(getValue(), playerId.getValue());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(value);
    }
}
