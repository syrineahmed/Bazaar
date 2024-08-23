// src/main/java/tn/esprit/bazaar/entities/User.java
package tn.esprit.bazaar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    //@Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank
    private String lastName;
    //@Column(nullable = false)
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private boolean enabled;
    /*@Column(nullable = false)
    private LocalDate dateOfBirth;*/
    private String dateOfBirth;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
   // @Column(name = "phone_number")
   @NotBlank
        private String phoneNumber;
    private String pictureUrl;
    private Date createdDate;
    private Date updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}