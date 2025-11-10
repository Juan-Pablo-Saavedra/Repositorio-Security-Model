package com.sena.inventorysystem.UserManagement.Factory;

import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;

public class UserFactory {

    public static User createUser(String username, String email, String password, String firstName, String lastName, String phone, String address) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        return user;
    }

    public static User createUserFromDto(UserDto dto, String password) {
        return createUser(dto.getUsername(), dto.getEmail(), password, dto.getFirstName(), dto.getLastName(), dto.getPhone(), dto.getAddress());
    }

    public static UserDto createDtoFromUser(User user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                null, // No devolver contraseña por seguridad
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddress()
        );
    }
}