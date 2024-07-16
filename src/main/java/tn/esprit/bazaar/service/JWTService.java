package tn.esprit.bazaar.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
     String ExtractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token,UserDetails userDetails);



    }
