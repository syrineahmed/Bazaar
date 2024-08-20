package tn.esprit.bazaar.dto;

import lombok.Data;
import tn.esprit.bazaar.entities.User;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private User userDetails;
}
