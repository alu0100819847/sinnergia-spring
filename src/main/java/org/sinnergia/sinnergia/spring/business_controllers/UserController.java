package org.sinnergia.sinnergia.spring.business_controllers;

import org.sinnergia.sinnergia.spring.documents.Role;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.dto.*;
import org.sinnergia.sinnergia.spring.exceptions.ConflictException;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.sinnergia.sinnergia.spring.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Controller
public class UserController {
    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public UserController(UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public Mono<UserLandingDto> registerFromLanding(UserLandingDto userLandingDto){
        User user = new User();
        user.setEmail(userLandingDto.getEmail());
        user.setRoles(userLandingDto.getRoles());
        return Mono.when(this.noExistEmail(userLandingDto.getEmail()))
                .then(this.userRepository.save(user))
                .map(userSaved -> new UserLandingDto(userSaved.getEmail(), userSaved.getRoles()));
    }


    public Mono<JwtDto> login(UserLoginDto userLoginDto){
        return Mono
                .when(this.checkCredential(userLoginDto))
                .then(this.getToken(userLoginDto));
    }

    public Mono<JwtDto> getToken(UserLoginDto userLoginDto) {
        return this.userRepository.findOneByEmail(userLoginDto.getEmail()).map(
                user -> {
                        String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
                        return new JwtDto(jwtService.createToken(roles, user.getEmail()));
                }
        );
    }

    public Mono<Void> checkCredential(UserLoginDto userLoginDto){
        return this.findUserAssuredForLogin(userLoginDto.getEmail())
                .handle((last, sink) -> {
                    if(new BCryptPasswordEncoder().matches(userLoginDto.getPassword(),last.getPassword())) {
                        sink.complete();
                    }
                    else {
                        sink.error(new CredentialException("User or Password incorrect."));
                    }
                }
        );
    }

    public Mono<Void> register(UserRegisterDto userRegisterDto){
        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        return Mono.when(this.noExistEmail(userRegisterDto.getEmail())).then(this.userRepository.save(user)).then();
    }

    public Mono<Void> noExistEmail(String email){
        return this.userRepository.findOneByEmail(email)
                .handle((result, sink) -> sink.error(new ConflictException("Email already exists.")));
    }

    public Mono<UserLoginDto> findUserAssuredForLogin(String email){
        return this.userRepository.findOneByEmail(email)
                .switchIfEmpty(Mono.error(new CredentialException("User or Password incorrect.")))
                .map(user -> new UserLoginDto(user.getEmail(), user.getPassword()));

    }

    public Flux<UserAdminDto> readAll() {
        return this.userRepository.findAll()
                .map(user -> new UserAdminDto(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getRoles(), user.getRegistrationDate()));
    }

    public Mono<Void> deleteAll(){
        return this.userRepository.deleteAll();
    }

    public Mono<Void> delete(String email) {
        return this.userRepository.deleteOneByEmail(email);
    }

    public Mono<Void> update(UserAdminDto userAdminDto) {
        Mono<User> updateUser = this.userRepository.findById(userAdminDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Article id " + userAdminDto.getId())))
                .map(user -> {
                    user.setEmail(userAdminDto.getEmail());
                    user.setName(userAdminDto.getName());
                    user.setSurname(userAdminDto.getSurname());
                    user.setRoles(userAdminDto.getRoles());
                    return user;
                });
        return this.userRepository.saveAll(updateUser).then();
    }
}
