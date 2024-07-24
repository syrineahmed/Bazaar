package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.JwtAuthenticationResponse;
import tn.esprit.bazaar.dto.RefreshTokenRequest;
import tn.esprit.bazaar.dto.SigninRequest;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest) ;
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;



    }
