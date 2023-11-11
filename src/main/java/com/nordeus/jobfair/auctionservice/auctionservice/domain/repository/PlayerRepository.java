package com.nordeus.jobfair.auctionservice.auctionservice.domain.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.player.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, PlayerId> {
}
