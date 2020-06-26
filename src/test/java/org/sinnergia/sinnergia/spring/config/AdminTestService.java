package org.sinnergia.sinnergia.spring.config;

import org.sinnergia.sinnergia.spring.api_rest_controllers.UserResource;
import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.dto.JwtDto;
import org.sinnergia.sinnergia.spring.dto.UserLoginDto;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@Service
public class AdminTestService {

    private JwtDto jwtDto;

    public WebTestClient login(WebTestClient webTestClient) {
        if(jwtDto == null){
            this.jwtDto = webTestClient
                    .post().uri(UserResource.USERS)
                    .body(BodyInserters.fromValue(new UserLoginDto("adminuser@sinnergia.org", "adminuser")))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(JwtDto.class)
                    .returnResult().getResponseBody();
        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.jwtDto.getToken()).build();
    }
}
