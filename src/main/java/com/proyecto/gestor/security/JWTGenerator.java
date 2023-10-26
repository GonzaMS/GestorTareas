package com.proyecto.gestor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    //Este metodo genera un token JWT basado en la informacion de autenticacion del usuario
    public String generateToken(Authentication authentication) {
        //Obtiene el nombre de usuario del objeto Authentication
        String username = authentication.getName();

        //Obtiene la fecha actual y calcula la fecha de expiracion del token
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        //Crea un token JWT con el nombre de usuario, la fecha de emision, la fecha de expiracion y la firma
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }

    //Este metodo extrae el nombre de usuario del token JWT
    public String getUsernameFromJWT(String token) {
        //Utiliza la clave secreta para verificar la firma y extraer los (claims) del token
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        //Obtiene el nombre de usuario de los claims
        return claims.getSubject();
    }

    // Este metodo verifica si un token JWT es valido y no ha caducado
    public boolean validateToken(String token) {
        try {
            //Intenta analizar el token JWT y verifica su firma
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            //Si se produce una excepcion, se considera si el token esta expirado o incorrecto
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
