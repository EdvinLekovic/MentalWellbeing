package com.example.backend.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,USER,CREATOR;
    @Override
    public String getAuthority() {
        return name();
    }
}
