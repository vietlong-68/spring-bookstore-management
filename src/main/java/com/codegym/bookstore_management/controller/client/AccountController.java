package com.codegym.bookstore_management.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.model.UserS;
import com.codegym.bookstore_management.service.SomethingConverter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
    private final SomethingConverter somethingConverter;

    public AccountController(SomethingConverter somethingConverter) {
        this.somethingConverter = somethingConverter;
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
        User user = somethingConverter.userStoUser(userS);
        model.addAttribute("user", user);
        return "client/account/edit-profile";
    }

}
