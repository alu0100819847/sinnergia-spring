package org.sinnergia.sinnergia.spring.business_controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.TestConfig;
import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.dto.UserAdminDto;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.dto.UserLoginDto;
import org.sinnergia.sinnergia.spring.dto.UserRegisterDto;
import org.sinnergia.sinnergia.spring.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@TestConfig
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void deleteAll(){
        StepVerifier
                .create(this.userController.deleteAll())
                .expectComplete()
                .verify();
    }

    @Test
    void testRegisterFromLanding(){
        Role[] roles = {Role.CUSTOMER};
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landing@example.com", roles)))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void testRegisterFromLandingWithConflictException(){
        Role[] roles = {Role.CUSTOMER};
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landingCreateRepeated@example.com", roles)))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landingCreateRepeated@example.com", roles)))
                .expectErrorMatches( err -> {
                    assertEquals("Conflict Exception (409). Email already exists.", err.getMessage());
                    return true;
                })
                .verify();
    }

    @Test
    void testRegister(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("registerTest@example.com", "registertest", "registertest")))
                .expectComplete()
                .verify();
    }

    @Test
    void testRegisterWithRepeatedEmail(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("registerTest2@example.com", "registertest", "registertest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.register(new UserRegisterDto("registerTest2@example.com", "registertest", "registertest")))
                .expectErrorMatches( err -> {
                    assertEquals("Conflict Exception (409). Email already exists.", err.getMessage());
                    return true;
                })
                .verify();
    }


    @Test
    void testLoginUserWithCredentialException(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("loginTest2@example.com", "logintest", "logintest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.checkCredential(new UserLoginDto("loginTest2@example.com", "logintest---3121")))
                .expectErrorMatches( err -> {
                    assertEquals("Credential Exception (401). User or Password incorrect.", err.getMessage());
                    return true;
                })
                .verify();

        StepVerifier
                .create(this.userController.checkCredential(new UserLoginDto("loginTest22121@example.com", "logintest")))
                .expectErrorMatches( err -> {
                    assertEquals("Credential Exception (401). User or Password incorrect.", err.getMessage());
                    return true;
                })
                .verify();
    }

    @Test
    void testGetToken(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("tokenTest@example.com", "tokenTest", "tokenTest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.getToken(new UserLoginDto("tokenTest@example.com", "tokenTest")))
                .expectNextMatches( token -> {
                        assertEquals("tokenTest@example.com", this.jwtService.verify("Bearer " + token.getToken()).getClaim("user").asString());
                        assertEquals(Role.CUSTOMER.toString(), this.jwtService.verify("Bearer " + token.getToken()).getClaim("roles").asList(String.class).get(0));
                        return true;
                    }
                )
                .expectComplete()
                .verify();
    }

    @Test
    void testLoginUser(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("loginTest@example.com", "logintest", "logintest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.checkCredential(new UserLoginDto("loginTest@example.com", "logintest")))
                .expectComplete()
                .verify();

    }

    @Test
    void testReadAll(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("readAllTest@example.com", "readAllTest", "readAllTest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.readAll())
                .expectNextCount(1)
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.register(new UserRegisterDto("readAllTest1@example.com", "readAllTest", "readAllTest")))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.userController.readAll())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void testUpdateUser(){
        StepVerifier
                .create(this.userController.register(new UserRegisterDto("updateUserTest@example.com", "updateUserTest", "updateUserTest")))
                .expectComplete()
                .verify();
        UserAdminDto userAdminDto = this.userController.readAll().blockFirst();
        assertNotNull(userAdminDto);
        userAdminDto.setName("testingUpdate");
        userAdminDto.setSurname("in userController");
        userAdminDto.setRoles(new Role[]{Role.CUSTOMER, Role.ADMIN});
        StepVerifier
                .create(this.userController.update(userAdminDto))
                .expectComplete()
                .verify();
        UserAdminDto userAdminDto2 = this.userController.readAll().blockFirst();
        assertNotNull(userAdminDto2);
        assertEquals("testingUpdate", userAdminDto2.getName());
        assertEquals("in userController", userAdminDto2.getSurname());
        assertArrayEquals(new Role[]{Role.CUSTOMER, Role.ADMIN}, userAdminDto2.getRoles());
    }

}
