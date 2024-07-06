package de.gamergrotte.steam.widget.repository;

import de.gamergrotte.steam.widget.entity.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HitRepository extends JpaRepository<Hit, String>, JpaSpecificationExecutor<Hit> {

}