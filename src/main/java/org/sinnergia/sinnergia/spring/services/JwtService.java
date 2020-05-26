package org.sinnergia.sinnergia.spring.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sinnergia.sinnergia.spring.repositories.JWTConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private JWTConfigRepository repository;

    private static final int EXPIRES_TIME = 1080000;
    private static final String ISSUER = "auth0";
    private static final String USER = "user";
    private static final String ROLES = "roles";

    @Autowired
    public JwtService(JWTConfigRepository repository) {
        this.repository = repository;
    }

    public String createToken(String[] roles, String email) {


        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_TIME))
                .withClaim(USER, email)
                .withArrayClaim(ROLES, roles)
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256("secret"))
                    .withIssuer(ISSUER).build().verify(token);
        } catch (Exception exception) {
            throw new JWTVerificationException("JWT is wrong. " + exception.getMessage());
        }

    }
}
