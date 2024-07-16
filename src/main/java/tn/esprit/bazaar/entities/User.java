package tn.esprit.bazaar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;


    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String pictureUrl;
    private Date createdDate;
    private Date updatedDate;
    private boolean isActive;
    String passwordResetToken;
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
        return true;    }

    @Override
    public boolean isAccountNonLocked() {
        return true;    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
