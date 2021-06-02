package pl.bartekde.loelix.appuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bartekde.loelix.advert.Advertisement;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 255, nullable = true)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 15, nullable = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private Set<Advertisement> advertisements;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
