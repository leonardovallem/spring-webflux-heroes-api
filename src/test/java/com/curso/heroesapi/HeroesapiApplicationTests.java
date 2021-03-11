package com.curso.heroesapi;

import com.curso.heroesapi.constants.HeroesConstant;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
@AutoConfigureWebClient		// problemas de compatibilidade, @AutoConfigureWebTestClient não está mais entre as dependências
class HeroesapiApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getOneHeroById() {
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"), "604795ec1b5ec13ac8f90511")
				.exchange()
				.expectStatus().isOk()
				.expectBody();
	}

	@Test
	void getOneHeroNotFound() {
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"), "unpresent_id")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void deleteHero() {
		webTestClient.delete().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"), "unpresent_id")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Void.class);
	}

}
