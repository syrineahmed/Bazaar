package tn.esprit.bazaar.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.entities.Role;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.UserService;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(("User not found")));
            }
        };

    }

    ///////// list of users  (admin) ///////////////////+
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //////// admin can delete the user ////////////
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    /////admin can update the role of the user/////////////
    public void updateUserRole(String email, Role newRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        user.setRole(newRole);
        userRepository.save(user);
    }

    private boolean isAdmin(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .equals(Role.ADMIN);
    }

    ///admin can get user by email //////
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    ///admin can get user by id //////
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    ///admin can search for users /////
    @Override
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.searchByTerm(searchTerm);
        }
    }
}