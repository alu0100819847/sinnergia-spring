package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ApiTestConfig
public class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;



    @Test
    void testRegisterLandingUser(){
        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUser@example.com", "consumer")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testRegisterLandingUserWithConflictException(){
        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUserRepeated@example.com", "consumer")))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUserRepeated@example.com", "consumer")))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testRegisterLandingUserWithBadRequestException(){
        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLandingDto(null, "consumer")))
                .exchange()
                .expectStatus().isBadRequest();
    }
}
