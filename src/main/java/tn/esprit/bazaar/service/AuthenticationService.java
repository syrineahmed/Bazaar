package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.entities.User;

public interface AuthenticationService {
    public User signup(SignUpRequest signUpRequest);

}
