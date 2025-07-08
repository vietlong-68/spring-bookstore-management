package com.codegym.bookstore_management.model.dto;

import com.codegym.bookstore_management.service.validator.PasswordMatches;
import com.codegym.bookstore_management.service.validator.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches(message = "Mật khẩu xác nhận không khớp")
public class RegisterDTO {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @UniqueEmail(message = "Email đã được sử dụng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 30, message = "Mật khẩu phải có từ 6 đến 30 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự")
    private String fullName;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "Số điện thoại không hợp lệ (ví dụ: 0987654321 hoặc +84987654321)")
    private String phone;

    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }
}