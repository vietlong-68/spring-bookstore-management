package com.codegym.bookstore_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegym.bookstore_management.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
