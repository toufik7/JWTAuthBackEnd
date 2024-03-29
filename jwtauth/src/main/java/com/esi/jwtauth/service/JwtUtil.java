package com.esi.jwtauth.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUtil {

    private String secret;
    private int jwtExpirationInMs;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATION"))) {
            claims.put("isAdministration", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_MEDECIN"))) {
            claims.put("isMedecin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_AIDE_SOIGNANT"))) {
            claims.put("isAideSoignant", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            claims.put("isPatient", true);
        }
        // add other roles here
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public boolean validateToken(String authToken) {
        try {
            // Jwt token has not been tampered with
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isAdministration = claims.get("isAdministration", Boolean.class);
        Boolean isMedecin = claims.get("isMedecin", Boolean.class);
        Boolean isAideSoignant = claims.get("isAideSoignant", Boolean.class);
        Boolean isPatient = claims.get("isPatient", Boolean.class);
        if (isAdmin != null && isAdmin == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (isAdministration != null && isAdministration == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMINISTRATION"));
        }
        if (isMedecin != null && isMedecin == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_MEDECIN"));
        }
        if (isAideSoignant != null && isAideSoignant == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_AIDE_SOIGNANT"));
        }
        if (isPatient != null && isPatient == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_PATIENT"));
        }
        return roles;
    }


}
