package org.sinnergia.sinnergia.spring.api_rest_controllers;


import org.sinnergia.sinnergia.spring.business_controllers.UserController;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.logging.LogManager;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS ="/users";

    private UserController userController;

    @Autowired
    public UserResource(UserController userController){
        this.userController = userController;
    }

    @PostMapping
    public Mono<UserLandingDto> registerLandingUser(@Valid @RequestBody UserLandingDto userLandingDto){
        return this.userController.registerFromLanding(userLandingDto);
    }
}
