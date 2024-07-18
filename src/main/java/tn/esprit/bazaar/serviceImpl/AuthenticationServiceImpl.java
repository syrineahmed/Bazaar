package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.JwtAuthenticationResponse;
import tn.esprit.bazaar.dto.RefreshTokenRequest;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.dto.SigninRequest;
import tn.esprit.bazaar.entities.Role;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.AuthenticationService;
import tn.esprit.bazaar.service.JWTService;

import java.util.Date;
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
        user.setCreatedDate(new Date());//hedhi 3leh badeltha 5ater mech user yhot date de creation mta3 compte tethat wahadha b date mta3 taw
        user.setUpdatedDate(new Date());//wel update date b3d par exemple ki bech ya3mel modifier lel profile mte3ou tzidou stare hedha fel methode mta3 update profile
       // user.setActive(signUpRequest.isActive());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEnabled(true);
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


    }
    private User convertToUserDto(User user) {
        User dto = new User();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        return dto;
    }
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.ExtractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;
    }
}