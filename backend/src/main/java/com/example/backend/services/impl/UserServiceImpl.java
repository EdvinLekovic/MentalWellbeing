package com.example.backend.services.impl;

import com.example.backend.models.Image;
import com.example.backend.models.User;
import com.example.backend.models.exceptions.InvalidPasswordException;
import com.example.backend.models.exceptions.InvalidUsernameException;
import com.example.backend.models.exceptions.PasswordsDoNotMatchException;
import com.example.backend.models.exceptions.UsernameAlreadyExistsException;
import com.example.backend.models.requests.user_requests.EditUserRequest;
import com.example.backend.models.requests.user_requests.UserRequest;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> register(UserRequest userRequest) {

        if (userRequest.getUsername() == null || userRequest.getUsername().isEmpty())
            throw new InvalidUsernameException();

        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty())
            throw new InvalidPasswordException();

        if (this.userRepository.findById(userRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(userRequest.getUsername());
        }

        if (!userRequest.getPassword().equals(userRequest.getRepeatPassword())) {
            throw new PasswordsDoNotMatchException();
        }

        return Optional.of(userRepository.save(new User(userRequest.getUsername(),
                userRequest.getName(),
                userRequest.getLastName(),
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getRole())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findById(username);
    }

    @Override
    public Optional<User> editUserInformation(EditUserRequest editUserRequest) {
        User user = userRepository.findById(editUserRequest.getUsername()).orElseThrow();
        if (!editUserRequest.getName().isEmpty()) {
            user.setName(editUserRequest.getName());
        }
        if (!editUserRequest.getLastName().isEmpty()) {
            user.setLastName(editUserRequest.getLastName());
        }

        if (!editUserRequest.getPassword().isEmpty() &&
                editUserRequest.getPassword().equals(editUserRequest.getRepeatPassword())) {
            user.setPassword(passwordEncoder.encode(editUserRequest.getPassword()));
        }
        return Optional.of(userRepository.save(user));
    }
}
