package com.webapp.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class UserAccountAuth implements GrantedAuthority {
    private String auth;

    @Override
    public String getAuthority() {
        return auth;
    }
}
