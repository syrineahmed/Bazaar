package tn.esprit.bazaar.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.entities.Role;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.UserService;
import java.util.*;

@Service
@RequiredArgsConstructor
/*public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(("User not found")));
            }
        };



    }*/
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return user;
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
    //@Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    ///admin can get user by id //////
    //@Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    ///admin can search for users /////
   // @Override
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.searchByTerm(searchTerm);
        }
    }
    ///user can update his profile /////
   // @Override

    public User updateUser(User user) {
        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {

                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setDateOfBirth(user.getDateOfBirth());
                existingUser.setPhoneNumber(user.getPhoneNumber());
                existingUser.setPictureUrl(user.getPictureUrl());

                return userRepository.save(existingUser);
            }
        }
        return null;
    }

    ///user can change his password /////

    public User ChangePassword (Long idUser, String password) {

    User user = userRepository.findById(idUser).orElse(null);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    return userRepository.save(user);
    }

}