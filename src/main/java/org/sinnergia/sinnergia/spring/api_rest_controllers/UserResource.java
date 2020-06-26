package org.sinnergia.sinnergia.spring.api_rest_controllers;


import org.sinnergia.sinnergia.spring.business_controllers.UserController;
import org.sinnergia.sinnergia.spring.dto.*;
import org.sinnergia.sinnergia.spring.services.SeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String LANDING = "/landing";
    public static final String REGISTER = "/register";
    public static final String EMAIL ="/{email}";
    private UserController userController;
    private SeederService seederService;


    @Autowired
    public UserResource(UserController userController, SeederService seederService) {
        this.userController = userController;
        this.seederService = seederService;
    }
/*
    @PostConstruct
    private void constructor(){
        this.seederService.initialize();
    }
*/
    @PostMapping(value= LANDING)
    public Mono<UserLandingDto> registerLandingUser(@Valid @RequestBody UserLandingDto userLandingDto){
        return this.userController.registerFromLanding(userLandingDto);
    }

    @PostMapping
    public Mono<JwtDto> loginUser(@Valid @AuthenticationPrincipal @RequestBody UserLoginDto userLoginDto){
        return this.userController.login(userLoginDto);
    }

    @PostMapping(value = REGISTER)
    public Mono<Void> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto){
        return this.userController.register(userRegisterDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Flux<UserAdminDto> getUsers(){
        return this.userController.readAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value= EMAIL)
    public Mono<Void> deleteUser(@PathVariable String email){
        return this.userController.delete(email);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public Mono<Void> updateUser(@Valid @RequestBody UserAdminDto userAdminDto){
        return this.userController.update(userAdminDto);
    }

}
