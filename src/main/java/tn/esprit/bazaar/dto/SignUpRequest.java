package tn.esprit.bazaar.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bazaar.entities.Gender;
import tn.esprit.bazaar.entities.Role;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
   // private String pictureUrl;
    // private Image image;
    private byte[] byteImg;
    private MultipartFile img;
    private Role role;
    private Gender gender;
   // private LocalDate dateOfBirth;
   private String dateOfBirth;

    // private Date createdDate;
  //  private Date updatedDate;
   // private boolean isActive;
    public void setImg(MultipartFile img) throws IOException {
        this.img = img;
        this.byteImg = img != null ? img.getBytes() : null;
    }

}
