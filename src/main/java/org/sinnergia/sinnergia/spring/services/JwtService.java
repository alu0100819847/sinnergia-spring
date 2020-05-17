package org.sinnergia.sinnergia.spring.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final int EXPIRES_TIME = 1080000;
    private static final String ISSUER = "auth0";
    public String createToken(String user, String name, String[] roles) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_TIME))
                .sign(algorithm);
    }
}
