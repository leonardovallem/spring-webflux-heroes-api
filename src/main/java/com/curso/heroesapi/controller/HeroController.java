package com.curso.heroesapi.controller;

import com.curso.heroesapi.document.Hero;
import com.curso.heroesapi.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.curso.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
@RequestMapping("hero")
@Slf4j
public class HeroController {
    @Autowired
    private HeroService heroService;

    private static final Logger log = LoggerFactory.getLogger(HeroController.class);

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Hero> getAllItems() {
        log.info("requesting list of heroes");
        return heroService.findAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    public Mono<ResponseEntity<Hero>> findById(@PathVariable("id") String id) {
        log.info("requesting hero with id {}", id);
        return heroService.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code=HttpStatus.CREATED)
    public Mono<Hero> createHero(@RequestBody Hero hero) {
        log.info("creating a new hero");
        return heroService.save(hero);
    }

    @PutMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code=HttpStatus.OK)
    public Mono<Hero> updateHero(@RequestBody Hero hero) {
        return hero.getId() == null ? createHero(hero) :
                heroService.findById(hero.getId())
                .flatMap(item -> {
                    log.info("updating hero with id {}", hero.getId());
                    return heroService.save(hero);
                })
                .switchIfEmpty(createHero(hero));
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Mono<ResponseEntity<Hero>> deleteHero(@PathVariable("id") String id) {
        log.info("deleting hero with id {}", id);
        return heroService.findById(id)
                .map(item -> {
                    heroService.delete(item);
                    return new ResponseEntity<>(item, HttpStatus.OK);
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
