package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.dto.JwtAuthenticationResponse;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.dto.SigninRequest;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest)
    {

        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin( signinRequest));

    }

}
