package com.example.backend.models;

import com.example.backend.models.enums.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    private String username;
    private String name;
    private String lastName;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @ManyToOne
    private Image image;

    public User(String username, String name, String lastName, String password, Role role) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public String getFullName(){
        return name +" "+lastName;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
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
        return true;
    }
}
