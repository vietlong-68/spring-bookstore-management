# Hệ Thống Quản Lý Hiệu Sách Online

## Mô Tả Dự Án

Hệ thống quản lý hiệu sách online cho việc mua bán sách trực tuyến. Hệ thống hỗ trợ hai vai trò chính: **Khách hàng** (mua sách, quản lý giỏ hàng, đặt hàng) và **Quản trị viên** (quản lý sản phẩm, đơn hàng, người dùng).

## Tính Năng Chính

### Xác Thực & Phân Quyền

* **Đăng ký tài khoản**: Hỗ trợ validation email, mật khẩu
* **Đăng nhập/Đăng xuất**: Tích hợp Spring Security
* **Phân quyền**: ADMIN và USER với các chức năng riêng biệt
* **Remember Me**: Lưu trạng thái đăng nhập

### Chức Năng Khách Hàng

* **Duyệt sản phẩm**: Xem danh sách sách với thông tin chi tiết
* **Giỏ hàng**: Thêm/xóa sản phẩm, cập nhật số lượng
* **Đặt hàng**: Tạo đơn hàng với thông tin giao hàng
* **Lịch sử đơn hàng**: Xem và theo dõi trạng thái đơn hàng
* **Quản lý tài khoản**: Cập nhật thông tin cá nhân, avatar
* **Upload file**: Hỗ trợ upload avatar

### Chức Năng Quản Trị

* **Dashboard**: Tổng quan hệ thống với thống kê
* **Quản lý sản phẩm**: CRUD sách với upload hình ảnh
* **Quản lý đơn hàng**: Xem, cập nhật trạng thái đơn hàng
* **Quản lý người dùng**: CRUD tài khoản khách hàng
* **Upload file**: Hỗ trợ upload avatar và hình ảnh sản phẩm

## Công Nghệ Sử Dụng

### Backend Framework

* **Spring Boot 3.3.13**: Framework chính
* **Spring Security 6**: Bảo mật và xác thực
* **Spring Data JPA**: Truy cập dữ liệu
* **Spring Session JDBC**: Quản lý session

### Database & ORM

* **MySQL 8.0**: Hệ quản trị cơ sở dữ liệu
* **Hibernate**: ORM framework
* **Spring Session**: Lưu trữ session trong database

### Frontend & Template

* **Thymeleaf**: Template engine
* **Bootstrap**: Framework CSS cho giao diện
* **HTML5/CSS3**: Markup và styling

### Validation & Security

* **Bean Validation**: Validation dữ liệu
* **BCrypt**: Mã hóa mật khẩu
* **Custom Validators**: Validation tùy chỉnh

## Demo Scenarios

### Scenario 1: Khách Hàng Mua Sách

1. **Đăng ký tài khoản mới**

   * Điền thông tin cá nhân
   * Xác nhận email và mật khẩu
   * Hoàn tất đăng ký

2. **Duyệt và mua sách**

   * Click vào sách để xem chi tiết
   * Thêm vào giỏ hàng với số lượng mong muốn
   * Xem giỏ hàng

3. **Đặt hàng**

   * Kiểm tra thông tin giỏ hàng
   * Điền thông tin giao hàng
   * Xác nhận đặt hàng
   * Nhận thông báo thành công

### Scenario 2: Quản Trị Viên Quản Lý Hệ Thống

1. **Đăng nhập admin**

   * Sử dụng tài khoản admin
   * Chuyển hướng đến dashboard

2. **Quản lý sản phẩm**

   * Thêm sách mới với hình ảnh
   * Cập nhật thông tin sách
   * Xóa sách không còn phù hợp

3. **Quản lý đơn hàng**

   * Cập nhật trạng thái đơn hàng
   * Xem chi tiết đơn hàng

4. **Quản lý người dùng**

   * Xem danh sách khách hàng
   * Cập nhật thông tin người dùng
   * Phân quyền và quản lý vai trò

### Scenario 3: Quản Lý Tài Khoản Cá Nhân

1. **Xem thông tin cá nhân**

   * Xem thông tin hiện tại

2. **Cập nhật thông tin**

   * Chỉnh sửa thông tin cá nhân
   * Upload avatar mới
   * Lưu thay đổi

3. **Xem lịch sử đơn hàng**

   * Xem chi tiết từng đơn hàng
   * Theo dõi trạng thái giao hàng

## Đóng Góp

Dự án này được phát triển như một case study cho việc học Java Spring. Mọi đóng góp và góp ý đều được chào đón!
