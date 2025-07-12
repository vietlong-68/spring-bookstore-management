package com.codegym.bookstore_management.controller.client;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.codegym.bookstore_management.service.OrderService;
import com.codegym.bookstore_management.model.Order;
import com.codegym.bookstore_management.model.OrderDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccountController {
    private final SomethingConverter somethingConverter;
    private final RoleService roleService;
    private final UserService userService;
    private final UploadService uploadService;
    private final OrderService orderService;

    public AccountController(SomethingConverter somethingConverter, RoleService roleService, UserService userService,
            UploadService uploadService, OrderService orderService) {
        this.somethingConverter = somethingConverter;
        this.roleService = roleService;
        this.userService = userService;
        this.uploadService = uploadService;
        this.orderService = orderService;
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

    @GetMapping("/profile/order-history")
    public String showOrderHistory(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User user = somethingConverter.userStoUser(userS);
        List<Order> orders = orderService.findOrderByUser(user);
        model.addAttribute("orders", orders);
        return "client/account/order-history";
    }

    @GetMapping("/profile/order-history/{orderId}")
    public String showOrderDetail(@PathVariable("orderId") Long orderId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserS userS = (UserS) session.getAttribute("currentUser");
        if (userS == null) {
            return "redirect:/login";
        }
        User user = somethingConverter.userStoUser(userS);
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "redirect:/profile/order-history?error=notFound";
        }
        model.addAttribute("order", order);
        return "client/account/order-detail";
    }
}
