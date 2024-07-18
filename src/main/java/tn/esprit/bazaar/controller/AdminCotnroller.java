package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.dto.MessageResponse;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.service.UserService;
import tn.esprit.bazaar.serviceImpl.UserServiceImpl;
import tn.esprit.bazaar.entities.Role;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminCotnroller {
    @Autowired
 UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;
    //testing
    @GetMapping
    public ResponseEntity<String>  sayHello() {
        return ResponseEntity.ok("Hello admin");
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/users/{id}")

    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body(new MessageResponse("User deleted successfully"));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @PutMapping("/users/{email}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable String email, @RequestParam String newRole) {
        try {
            userServiceImpl.updateUserRole(email, Role.valueOf(newRole));
            return ResponseEntity.ok().body(new MessageResponse("User role updated successfully"));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
    @GetMapping("/users/byemail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam(required = false) String searchTerm) {
        List<User> users = userServiceImpl.searchUsers(searchTerm);
        return ResponseEntity.ok(users);
    }

}
