package com.codegym.bookstore_management.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.model.dto.RegisterDTO;

@Service
public class SomethingConverter {

    private final UserService userService;

    public SomethingConverter(UserService userService) {
        this.userService = userService;
    }

    public User registerDTOToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setFullName(registerDTO.getFullName());
        user.setAddress(registerDTO.getAddress());
        user.setPhone(registerDTO.getPhone());
        return user;
    }

    public User getCurrentUserFromUserDetails(UserDetails userDetails) {

        String email = userDetails.getUsername();
        User currenUser = userService.getUserByEmail(email);
        if (currenUser == null) {
            throw new RuntimeException("User not found");
        }
        return currenUser;
    }

    public static UserS UsertoUserS(User user) {
        if (user == null) {
            return null;
        }
        UserS userS = new UserS();
        userS.setId(user.getId());
        userS.setEmail(user.getEmail());
        userS.setPassword(user.getPassword()); 
        userS.setFullName(user.getFullName());
        userS.setAddress(user.getAddress());
        userS.setPhone(user.getPhone());
        userS.setAvatar(user.getAvatar());
        if (user.getRole() != null) {
            userS.setRoleId(user.getRole().getId());
        }
        return userS;
    }
}
