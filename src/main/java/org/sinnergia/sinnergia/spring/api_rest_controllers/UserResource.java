package org.sinnergia.sinnergia.spring.api_rest_controllers;


import org.sinnergia.sinnergia.spring.business_controllers.UserController;
import org.sinnergia.sinnergia.spring.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String LANDING = "/landing";
    public static final String REGISTER = "/register";
    public static final String EMAIL ="/{email}";
    private UserController userController;

    @Autowired
    public UserResource(UserController userController){
        this.userController = userController;
    }


    @PostMapping(value= LANDING)
    public Mono<UserLandingDto> registerLandingUser(@Valid @RequestBody UserLandingDto userLandingDto){
        return this.userController.registerFromLanding(userLandingDto);
    }

    @PostMapping
    public Mono<JwtDto> loginUser(@Valid @RequestBody UserLoginDto userLoginDto){
        return this.userController.login(userLoginDto);
    }

    @PostMapping(value = REGISTER)
    public Mono<Void> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto){
        return this.userController.register(userRegisterDto);
    }

    @GetMapping
    public Flux<UserAdminDto> getUsers(){
        return this.userController.readAll();
    }

    @DeleteMapping(value= EMAIL)
    public Mono<Void> deleteUser(@PathVariable String email){
        return this.userController.delete(email);
    }

}
