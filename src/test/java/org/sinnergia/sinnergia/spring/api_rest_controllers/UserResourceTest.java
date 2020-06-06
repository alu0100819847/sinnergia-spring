package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.dto.UserAdminDto;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.dto.UserLoginDto;
import org.sinnergia.sinnergia.spring.dto.UserRegisterDto;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ApiTestConfig
class UserResourceTest {

    @Autowired
    private WebTestClient webTestClient;



    @Test
    void testRegisterLandingUser(){
        Role[] roles = {Role.CUSTOMER};
        UserLandingDto userLandingDto = new UserLandingDto("apiLandingUser@example.com", roles);
        webTestClient
                .post().uri(UserResource.USERS + UserResource.LANDING)
                .body(BodyInserters.fromValue(userLandingDto))
                .exchange()
                .expectStatus().isOk();

        this.deleteAll(userLandingDto.getEmail());
    }

    @Test
    void testRegisterLandingUserWithConflictException(){
        Role[] roles = {Role.CUSTOMER};
        UserLandingDto userLandingDto = new UserLandingDto("apiLandingUserRepeated@example.com", roles);
        webTestClient
                .post().uri(UserResource.USERS + UserResource.LANDING)
                .body(BodyInserters.fromValue(userLandingDto))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri(UserResource.USERS+ UserResource.LANDING)
                .body(BodyInserters.fromValue(new UserLandingDto("apiLandingUserRepeated@example.com", roles)))
                .exchange()
                .expectStatus().is5xxServerError();

        this.deleteAll(userLandingDto.getEmail());
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
        UserRegisterDto userRegisterDto = new UserRegisterDto("testRegisterApi@example.com", "testRegisterholaaa", "testRegisterholaaa");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();
        this.deleteAll(userRegisterDto.getEmail());
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
        this.deleteAll(userRegisterDto.getEmail());
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


    @Test
    void testReadAll(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("testReadAllApi@example.com", "testReadAllApi", "testReadAllApi");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .get().uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk();

        this.deleteAll(userRegisterDto.getEmail());
    }

    @Test
    void testUpdateUser(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("testUpdateUserApi@example.com", "testUpdateUserApi", "testUpdateUserApi");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();

        UserAdminDto userAdminDto =
                webTestClient
                    .get().uri(UserResource.USERS)
                    .exchange()
                    .expectStatus().isOk()
                            .expectBodyList(UserAdminDto.class)
                            .returnResult()
                    .getResponseBody().get(0);

        assertNotNull(userAdminDto);
        userAdminDto.setName("updateUser");
        userAdminDto.setSurname("in Api");

        webTestClient
                .put().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(userAdminDto))
                .exchange()
                .expectStatus().isOk();
        this.deleteAll(userAdminDto.getEmail());
    }


    @Test
    void testUpdateUserWithNotFoundException(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("testUpdateUserWithNotFoundExceptionApi@example.com", "testUpdateUserApi", "testUpdateUserApi");
        webTestClient
                .post().uri(UserResource.USERS + UserResource.REGISTER)
                .body(BodyInserters.fromValue(userRegisterDto))
                .exchange()
                .expectStatus().isOk();

        UserAdminDto userAdminDto =
                webTestClient
                        .get().uri(UserResource.USERS)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(UserAdminDto.class)
                        .returnResult()
                        .getResponseBody().get(0);

        assertNotNull(userAdminDto);
        userAdminDto.setId("000000000000000");
        webTestClient
                .put().uri(UserResource.USERS)
                .body(BodyInserters.fromValue(userAdminDto))
                .exchange()
                .expectStatus().is5xxServerError();
        this.deleteAll(userAdminDto.getEmail());
    }

    void deleteAll(String... emails){
        for(String email : emails){
            webTestClient
                    .delete().uri(UserResource.USERS + "/" + email)
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
