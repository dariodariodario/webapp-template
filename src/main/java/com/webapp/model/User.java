package com.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(indexes = {
        @Index(name = "usernameIndex", columnList = "username"),
        @Index(name = "emailIndex", columnList = "email")
}, name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    @JsonIgnore
    private String password;

    private String email;

    @Builder.Default
    private boolean enabled = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private Set<UserAccountAuth> auths = null;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public static User fromAuthentication(Authentication authentication) {
        if (authentication == null)
            return null;
        return (User) authentication.getPrincipal();
    }

}
