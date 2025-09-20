package com.codegym.bookstore_management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import com.codegym.bookstore_management.model.Role;
import com.codegym.bookstore_management.service.RoleService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;

    public DataInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        if (!roleExists("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Quản trị viên");
            roleService.save(adminRole);
            System.out.println("Đã tạo role ADMIN");
        }

        if (!roleExists("USER")) {
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("Khách hàng thông thường");
            roleService.save(userRole);
            System.out.println("Đã tạo role USER");
        }
    }

    private boolean roleExists(String roleName) {
        List<Role> allRoles = roleService.findAll();
        for (Role role : allRoles) {
            if (roleName.equals(role.getName())) {
                return true;
            }
        }
        return false;
    }
}
