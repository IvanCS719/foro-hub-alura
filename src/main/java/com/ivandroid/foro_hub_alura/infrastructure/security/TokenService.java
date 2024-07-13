package com.ivandroid.foro_hub_alura.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ivandroid.foro_hub_alura.domain.user.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Foro Hub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFecha())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
           throw new RuntimeException();
        }
    }

    private Instant generarFecha(){
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-06:00"));
    }
}
