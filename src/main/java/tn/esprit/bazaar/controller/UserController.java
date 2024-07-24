package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
//testing
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello user");
    }
    @PutMapping("/updateprofile")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update user");
        }
    }
    @PutMapping("/changepassword/{idUser}/{password}")
    public User ChangePassword(@PathVariable Long idUser, @PathVariable String  password) {
        return userService.ChangePassword(idUser, password);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getCurrentUserProfile() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }




}
