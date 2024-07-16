package tn.esprit.bazaar.dto;

import lombok.Data;
import tn.esprit.bazaar.entities.Gender;
import tn.esprit.bazaar.entities.Role;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String pictureUrl;
    // private Image image;
    private Role role;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Date createdDate;
    private Date updatedDate;
    private boolean isActive;

}
