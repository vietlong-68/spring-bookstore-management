package com.codegym.bookstore_management.controller.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.dto.RegisterDTO;
import com.codegym.bookstore_management.service.SomethingConverter;
import com.codegym.bookstore_management.service.RoleService;
import com.codegym.bookstore_management.service.UploadService;
import com.codegym.bookstore_management.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final SomethingConverter somethingConverter;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UploadService uploadService;

    public AuthController(SomethingConverter mapperService, PasswordEncoder passwordEncoder, RoleService roleService,
            UploadService uploadService, UserService userService) {
        this.somethingConverter = mapperService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.uploadService = uploadService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        RegisterDTO userRegisterDTO = new RegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") @Valid RegisterDTO userRegisterDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = somethingConverter.registerDTOToUser(userRegisterDTO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setRole(roleService.findById(2L).get());
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String handleAccessDeny() {
        return "client/auth/access_deny";
    }

}
