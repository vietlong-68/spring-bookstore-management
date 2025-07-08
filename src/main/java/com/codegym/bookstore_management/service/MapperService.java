package com.codegym.bookstore_management.service;

import org.springframework.stereotype.Service;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.dto.RegisterDTO;

@Service
public class MapperService {
    public User registerDTOToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setFullName(registerDTO.getFullName());
        user.setAddress(registerDTO.getAddress());
        user.setPhone(registerDTO.getPhone());
        return user;
    }
}
