package org.sinnergia.sinnergia.spring.business_controllers;


import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.exceptions.ConflictException;
import org.sinnergia.sinnergia.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Mono<UserLandingDto> registerFromLanding(UserLandingDto userLandingDto){
        User user = new User();
        user.setEmail(userLandingDto.getEmail());
        user.setRoles(userLandingDto.getRoles());
        return Mono.when(this.noExistEmail(userLandingDto.getEmail()))
                .then(this.userRepository.save(user))
                .map(userSaved -> new UserLandingDto(userSaved.getEmail(), userSaved.getRoles()));
    }

    public Mono<Void> noExistEmail(String email){
        return this.userRepository.findOneByEmail(email)
                .handle((result, sink) -> sink.error(new ConflictException("Email already exists.")));
    }
}
