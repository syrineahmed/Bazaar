package tn.esprit.bazaar.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
     String ExtractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token,UserDetails userDetails);

    String generateRefreshToken(Map<String,Object> extractClaims, UserDetails userDetails);


    }
