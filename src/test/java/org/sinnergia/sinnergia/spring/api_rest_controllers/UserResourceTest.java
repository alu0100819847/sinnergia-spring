package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.dto.UserLoginDto;
import org.sinnergia.sinnergia.spring.dto.UserRegisterDto;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


import static org.junit.Assert.assertEquals;


@ApiTestConfig
class UserResourceTest {

    @Autowired
    private WebTestClient webTestClient;



    @Test
    void testRegisterLandingUser(){
        Role[] roles = {Role.CUSTOMER};
        webTestClient
                .post().uri(UserResource.USERS + UserResource.LANDING)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUser@example.com", roles)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testRegisterLandingUserWithConflictException(){
        Role[] roles = {Role.CUSTOMER};
        webTestClient
                .post().uri(UserResource.USERS + UserResource.LANDING)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUserRepeated@example.com", roles)))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri(UserResource.USERS+ UserResource.LANDING)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUserRepeated@example.com", roles)))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testRegisterLandingUserWithBadRequestException(){
        Role[] roles = {Role.CUSTOMER};
        webTestClient
                .post().uri(UserResource.USERS + UserResource.LANDING)
                .body(BodyInserters.fromValue(new UserLandingDto(null, roles)))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testRegister(){
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(new UserRegisterDto("testRegisterApi@example.com", "testRegisterholaaa", "testRegisterholaaa")))
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void testRegisterWithBadRequestException(){
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(new UserRegisterDto("testRegisterApiIncorrectEmail", "testRegister", "testRegister")))
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(new UserRegisterDto(null, "testRegister", "testRegister")))
                .exchange()
                .expectStatus().isBadRequest();


        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(new UserRegisterDto("invalidPassword@example.com", "test", "test")))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testLogin(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("testLoginApi@example.com", "testLogin", "testLogin");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLoginDto("testLoginApi@example.com", "testLogin")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testLoginWithCredentialException(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("testLoginApiCredentialException@example.com", "testLogin", "testLogin");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLoginDto("testLoginApiCredentialException@example.com", "test1Login")))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(CredentialException.class)
                .value(error ->{
                    assertEquals(new CredentialException("Credential Exception (401). User or Password incorrect.").getMessage(), error.getMessage());
                });
    }

    @Test
    void testLoginUserWithBadRequestStatus(){
        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLoginDto(null, "testingNullEmail")))
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLoginDto("notAnEmail", "testingNotAnEmail")))
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(new UserLoginDto("invalidPassword@example.com", "test")))
                .exchange()
                .expectStatus().isBadRequest();
    }

}
