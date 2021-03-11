package com.curso.heroesapi.service;

import com.curso.heroesapi.document.Hero;
import com.curso.heroesapi.repository.HeroRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroService {
    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Flux<Hero> findAll() {
        return heroRepository.findAll();
    }

    public Mono<Hero> findById(String id) {
        return heroRepository.findById(id);
    }

    public Mono<Hero> save(Hero hero) {
        System.out.println(hero);
        return heroRepository.save(hero);
    }

    public Mono<Void> delete(Hero hero) {
        return heroRepository.delete(hero);
    }
}
