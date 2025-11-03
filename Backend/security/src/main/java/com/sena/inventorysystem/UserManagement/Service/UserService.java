package com.sena.inventorysystem.UserManagement.Service;

import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.Repository.UserRepository;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.DTO.AuthRequest;
import com.sena.inventorysystem.UserManagement.DTO.AuthResponse;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Usuario con username " + user.getUsername() + " ya existe");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Usuario con email " + user.getEmail() + " ya existe");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedBy("system");
        User savedUser = userRepository.save(user);

        return new AuthResponse(
                "user-token-" + savedUser.getId() + "-" + System.currentTimeMillis(),
                "Bearer",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BusinessException("Contraseña incorrecta");
        }

        return new AuthResponse(
                "user-token-" + user.getId() + "-" + System.currentTimeMillis(),
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public UserDto update(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con id: " + id));

        if (!existingUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Usuario con username " + user.getUsername() + " ya existe");
        }

        if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Usuario con email " + user.getEmail() + " ya existe");
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setUpdatedBy("system");

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("Usuario no encontrado con id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con id: " + id));
        return convertToDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con username: " + username));
        return convertToDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con email: " + email));
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getCreatedBy(),
                user.getUpdatedBy()
        );
    }
}