package tn.bazaar.dto;

import lombok.Data;
import tn.bazaar.entities.User;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    User userDetails;
}
