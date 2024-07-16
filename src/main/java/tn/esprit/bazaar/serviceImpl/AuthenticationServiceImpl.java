package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.SignUpRequest;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.AuthenticationService;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
  /*  private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final EmailService emailService;*/

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
        //user.setImage(signUpRequest.getImage());
        userRepository.save(user);

        return user;

    }
}
