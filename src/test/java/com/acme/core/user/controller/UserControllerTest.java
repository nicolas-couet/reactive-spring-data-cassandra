package com.acme.core.user.controller;

import com.acme.core.configuration.test.EmbeddedCassandraCoreIntegrationTest;
import com.acme.core.entity.EntityState;
import com.acme.core.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableWebFlux
public class UserControllerTest extends EmbeddedCassandraCoreIntegrationTest {

    private WebTestClient webTestClient;

    @Autowired
    ApplicationContext context;

    @BeforeAll
    public void setUp() {
        Consumer<ClientCodecConfigurer> consumer = configurer ->
                configurer.defaultCodecs().enableLoggingRequestDetails(true);

        this.webTestClient = WebTestClient.bindToApplicationContext(this.context)
                // deprecated but following spring documentation...
                .configureClient().exchangeStrategies(strategies -> strategies.codecs(consumer))
                .build();
    }

    @Test
    public void create(){
        String email = "email@acme.com";
        String password = "password";

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UserDTO(email, password)), UserDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(email)
                .jsonPath("$.state").isEqualTo(EntityState.ACTIVE.name())
                .jsonPath("$.password").isEqualTo(password);
    }
}
