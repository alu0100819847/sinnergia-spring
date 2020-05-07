package org.sinnergia.sinnergia.spring.business_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.TestConfig;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;

@TestConfig
public class UserControllerIT {

    @Autowired
    private UserController userController;

    @Test
    void testRegisterFromLanding(){
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landing@example.com", "consumer")))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void testRegisterFromLandingWithConflictException(){
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landingCreateRepeated@example.com", "consumer")))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        StepVerifier
                .create(this.userController.registerFromLanding(new UserLandingDto("landingCreateRepeated@example.com", "provider")))
                .expectErrorMatches( err -> {
                    assertEquals("Conflict Exception (409). Email already exists.", err.getMessage());
                    return true;
                })
                .verify();
    }


}
