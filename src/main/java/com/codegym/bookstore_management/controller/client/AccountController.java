package com.codegym.bookstore_management.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.model.Role;
import com.codegym.bookstore_management.service.RoleService;
import com.codegym.bookstore_management.service.UserService;
import com.codegym.bookstore_management.service.UploadService;
import com.codegym.bookstore_management.service.SomethingConverter;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccountController {
    private final SomethingConverter somethingConverter;
    private final RoleService roleService;
    private final UserService userService;
    private final UploadService uploadService;

    public AccountController(SomethingConverter somethingConverter, RoleService roleService, UserService userService,
            UploadService uploadService) {
        this.somethingConverter = somethingConverter;
        this.roleService = roleService;
        this.userService = userService;
        this.uploadService = uploadService;
    }

    @GetMapping("/profile")
    public String showProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        User user = somethingConverter.userStoUser(userS);
        model.addAttribute("user", user);
        return "client/account/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User user = somethingConverter.userStoUser(userS);
        model.addAttribute("user", user);
        return "client/account/edit-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User existingUser = userService.findUserById(userS.getId()).orElse(null);
        if (existingUser == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            user.setAvatar(existingUser.getAvatar());
            return "client/account/edit-profile";
        }
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarWebPath = uploadService.uploadFile(avatarFile, "avatar");
            if (avatarWebPath != null) {
                user.setAvatar(avatarWebPath);
            }
        }
        Role role = roleService.findById(userS.getRoleId()).orElse(null);
        user.setRole(role);
        user.setId(existingUser.getId());
        user.setPassword(existingUser.getPassword());
        userService.saveUser(user);

        UserS updatedUserS = somethingConverter.UsertoUserS(existingUser);
        session.setAttribute("currentUser", updatedUserS);

        return "redirect:/profile";
    }
}
