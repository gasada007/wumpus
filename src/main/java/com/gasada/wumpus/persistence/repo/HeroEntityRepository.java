package com.gasada.wumpus.persistence.repo;

import com.gasada.wumpus.persistence.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HeroEntityRepository extends JpaRepository<HeroEntity, Long> {
}
