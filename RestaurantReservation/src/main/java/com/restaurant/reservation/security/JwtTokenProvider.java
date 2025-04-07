package com.restaurant.reservation.security;

import com.restaurant.reservation.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Utilizamos Keys.secretKeyFor() para generar una clave segura de 512 bits
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generación automática de clave segura
    private static final long JWT_EXPIRATION = 86400000L; // 1 día en milisegundos

    /**
     * Genera un token JWT para el usuario.
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // El email del usuario será el subject del token
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)) // Fecha de expiración
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Firma usando el secretKey y el algoritmo HS512
                .compact();
    }

    /**
     * Extrae el email del token.
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder() // Usar parserBuilder() para evitar deprecated
                .setSigningKey(SECRET_KEY) // Clave secreta para validar el token
                .build()
                .parseClaimsJws(token) // Parsear el token
                .getBody();
        return claims.getSubject(); // Devuelve el subject (en este caso, el email)
    }

    /**
     * Valida el token JWT.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder() // Usar parserBuilder() para evitar deprecated
                .setSigningKey(SECRET_KEY) // Clave secreta para validar el token
                .build()
                .parseClaimsJws(token); // Si no lanza una excepción, el token es válido
            return true;
        } catch (Exception e) {
            return false; // El token no es válido
        }
    }
}
