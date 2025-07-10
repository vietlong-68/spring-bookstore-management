package com.codegym.bookstore_management.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserS implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String password;

    private String fullName;

    private String address;

    private String phone;

    private String avatar;

    private Long roleId;

}
