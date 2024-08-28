package tn.esprit.bazaar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.serviceImpl.UserServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002" , allowedHeaders = "*")
public class UserController {
    private final UserServiceImpl userService;
//testing
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello user");
    }
    @PutMapping("/updateprofile")
    public ResponseEntity<?> updateUser(
            @RequestParam("user") String userStr,
            @RequestParam(value = "img", required = false) MultipartFile img) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userStr, User.class);
            if (img != null) {
                user.setImage(img.getBytes());
            }
            User updatedUser = userService.updateUser(user);
            if (updatedUser != null) {
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to update user");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
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
