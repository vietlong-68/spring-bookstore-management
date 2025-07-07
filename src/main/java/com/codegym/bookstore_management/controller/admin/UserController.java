package com.codegym.bookstore_management.controller.admin;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Optional;
import java.util.UUID;
import com.codegym.bookstore_management.model.Role;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @Value("${app.images.upload-root}")
    private String imageUploadRoot;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
            try {
                String originalFilename = avatarFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    int lastDotIndex = originalFilename.lastIndexOf(".");
                    fileExtension = originalFilename.substring(lastDotIndex);
                }
                String newFileName = UUID.randomUUID().toString() + fileExtension;
                String avatarFolderPath = imageUploadRoot + "avatar" + File.separator;
                File uploadPath = new File(avatarFolderPath);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                File destFile = new File(uploadPath, newFileName);
                avatarFile.transferTo(destFile);
                String webImagePath = "/images/avatar/" + newFileName;
                user.setAvatar(webImagePath);
            } catch (Exception e) {
                e.printStackTrace();
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