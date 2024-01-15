package com.example.securityjwt.util;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;

public class AuthUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public void generatesJwtToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
    }
}
