package org.sinnergia.sinnergia.spring.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class JwtService {



    private static final int EXPIRES_TIME = 10800000;
    private static final String ISSUER = "auth0";
    public static final String USER = "user";
    public static final String ROLES = "roles";

    @Autowired
    public JwtService() {
        // Empty
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
                    .withIssuer(ISSUER).build().verify(token.substring("Bearer ".length()));
        } catch (Exception exception) {
            throw new JWTVerificationException("JWT is wrong. " + exception.getMessage());
        }
    }

    public Stream<String> getRoles(String token){
        return Arrays.stream(this.verify(token).getClaim(JwtService.ROLES).asArray(String.class));
    }
}
