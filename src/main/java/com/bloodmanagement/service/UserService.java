package com.bloodmanagement.service;

import com.bloodmanagement.dto.UserDTO;
import com.bloodmanagement.model.Role;
import com.bloodmanagement.model.User;
import com.bloodmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users and return as UserDTO
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toUserDTO) // Map each User to UserDTO
                .collect(Collectors.toList());
    }

    // Get a user by ID and return as UserDTO
    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::toUserDTO); // Map User to UserDTO
    }

    // Save a user and return as UserDTO
    public UserDTO saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return toUserDTO(savedUser); // Map the saved User to UserDTO
    }

    // Delete a user by ID
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Method to map User to UserDTO
    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getPoliticalId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().toString());
    }
}
