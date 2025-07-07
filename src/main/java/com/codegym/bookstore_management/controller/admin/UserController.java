package com.codegym.bookstore_management.controller.admin;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import com.codegym.bookstore_management.model.Role;
import com.codegym.bookstore_management.service.UploadService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UploadService uploadService;

    public UserController(UserService userService, RoleService roleService, UploadService uploadService) {
        this.userService = userService;
        this.roleService = roleService;
        this.uploadService = uploadService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/user/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/user/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            model.addAttribute("roles", roleService.findAll());
            return "admin/user/form";
        } else {
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role.id") Long roleId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile) {
        Role role = roleService.findById(roleId).orElseThrow();
        user.setRole(role);

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarWebPath = uploadService.uploadFile(avatarFile, "avatar");
            if (avatarWebPath != null) {
                user.setAvatar(avatarWebPath);
            }
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/detail/{id}")
    public String viewUserDetail(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/user/detail";
        } else {
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}