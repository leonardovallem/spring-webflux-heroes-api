package com.curso.heroesapi.repository;

import com.curso.heroesapi.document.Hero;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends ReactiveMongoRepository<Hero, String> {
}
