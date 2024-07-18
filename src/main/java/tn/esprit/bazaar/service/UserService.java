package tn.esprit.bazaar.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.bazaar.entities.User;

import java.util.List;

public interface UserService {
     UserDetailsService userDetailsService() ;
     void deleteUser(Long userId) ;
     User getUserByEmail(String email);
    User getUserById(Long id);
    List<User> searchUsers(String searchTerm);


    }
