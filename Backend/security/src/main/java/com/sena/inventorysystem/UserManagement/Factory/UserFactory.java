package com.sena.inventorysystem.UserManagement.Factory;

import com.sena.inventorysystem.UserManagement.Entity.User;

public class UserFactory {

    public static User createUser(String username, String email, String password, String firstName, String lastName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public static User createUserWithContact(String username, String email, String password, String firstName, String lastName, String phone, String address) {
        User user = createUser(username, email, password, firstName, lastName);
        user.setPhone(phone);
        user.setAddress(address);
        return user;
    }

    public static User createUserWithAudit(String username, String email, String password, String firstName, String lastName, String createdBy) {
        User user = createUser(username, email, password, firstName, lastName);
        user.setCreatedBy(createdBy);
        return user;
    }
}