package tn.bazaar.service;

import tn.bazaar.dto.JwtAuthenticationResponse;
import tn.bazaar.dto.RefreshTokenRequest;
import tn.bazaar.dto.SigninRequest;
import tn.bazaar.dto.SignUpRequest;
import tn.bazaar.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest) ;
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;



    }
