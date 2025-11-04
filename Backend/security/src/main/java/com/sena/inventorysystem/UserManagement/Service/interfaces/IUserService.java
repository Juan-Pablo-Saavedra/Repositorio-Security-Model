package com.sena.inventorysystem.UserManagement.Service.interfaces;

import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.Entity.User;

import java.util.List;

public interface IUserService {

    UserDto create(User user);

    UserDto update(Long id, User user);

    void delete(Long id);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto findByUsername(String username);

    UserDto findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}