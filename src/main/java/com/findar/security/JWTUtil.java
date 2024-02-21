package com.findar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET = Base64.getEncoder().encodeToString("Findar".getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("Findar")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public static String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validate(final String token){
        Claims claims = JWTUtil.getClaims(token);
        return claims.getExpiration().before(new Date())
                && claims.getIssuer().equals("Findar")
                && Jwts.parser().isSigned(token);
    }

    private static Claims getClaims(String t){
        return
                Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(t)
                        .getBody();
    }
}
