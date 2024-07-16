package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.JwtAuthenticationResponse;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.dto.SigninRequest;
import tn.esprit.bazaar.entities.Role;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.AuthenticationService;
import tn.esprit.bazaar.service.JWTService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;
   private final JWTService jwtService;


    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setPictureUrl(signUpRequest.getPictureUrl());
        user.setRole((signUpRequest.getRole()));
        user.setGender((signUpRequest.getGender()));
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setCreatedDate(signUpRequest.getCreatedDate());
        user.setUpdatedDate(signUpRequest.getUpdatedDate());
        user.setActive(signUpRequest.isActive());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return user;

    }


    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        System.err.println(user.isEnabled());
        if (!user.isEnabled()) {
            throw new IllegalArgumentException("Please confirm your account to proceed.");


        } else {
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);


            return jwtAuthenticationResponse;

        }

    }}
