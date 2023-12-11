package com.gasada.wumpus.service;

import com.gasada.wumpus.model.GameMap;
import com.gasada.wumpus.model.Hero;
import com.gasada.wumpus.persistence.entity.HeroEntity;
import com.gasada.wumpus.persistence.repo.HeroEntityRepository;
import com.gasada.wumpus.util.HelperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBService {

    private final HeroEntityRepository heroEntityRepository;

    public DBService(HeroEntityRepository heroEntityRepository) {
        this.heroEntityRepository = heroEntityRepository;
    }

    public void saveGame(Hero hero, GameMap gameMap) {
        HeroEntity heroEntity = HelperUtil.getHeroEntity(hero, gameMap);
        HeroEntity saved = heroEntityRepository.save(heroEntity);
        hero.setHeroId(saved.getHeroId());
    }

    public void leaderboard() {
        List<HeroEntity> heroEntities = heroEntityRepository.findAll();

        heroEntities = heroEntities.stream()
                .filter(HeroEntity::isHasGold)
                .sorted(Comparator.comparingInt(HeroEntity::getMoveCount))
                .collect(Collectors.toList());
        for (HeroEntity heroEntity : heroEntities) {
            System.out.println(
                    " PlayerName: " + heroEntity.getPlayerName() +
                    " MapSize: " + heroEntity.getGameMap().getSize() +
                    " HeroMoveCount: " + heroEntity.getMoveCount()
            );
        }
//        return heroEntities;
    }

    public List<HeroEntity> loadInProgressGames() {
        List<HeroEntity> heroEntities = heroEntityRepository.findAll();

        heroEntities = heroEntities.stream()
                .filter(heroEntity -> !heroEntity.isHasGold())
                .collect(Collectors.toList());

        return heroEntities;
    }
}
