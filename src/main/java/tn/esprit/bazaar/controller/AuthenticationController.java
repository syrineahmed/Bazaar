package tn.esprit.bazaar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bazaar.dto.JwtAuthenticationResponse;
import tn.esprit.bazaar.dto.RefreshTokenRequest;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.dto.SigninRequest;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002" , allowedHeaders = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestParam("signUpRequest") String signUpRequestStr,
            @RequestParam("img") MultipartFile img) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SignUpRequest signUpRequest = objectMapper.readValue(signUpRequestStr, SignUpRequest.class);
            signUpRequest.setImg(img);
            User createdUser = authenticationService.signup(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error signing up: " + e.getMessage());
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin( signinRequest));

    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));

    }

}
