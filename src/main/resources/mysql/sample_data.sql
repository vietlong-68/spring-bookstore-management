INSERT INTO roles (id, name, description) VALUES
  (1, 'ADMIN', 'Quản trị viên'),
  (2, 'USER', 'Khách hàng thông thường');
INSERT INTO users (id, email, password, full_name, address, phone, avatar, role_id) VALUES
  (1, 'admin@bookstore.com', 'a123', 'Việt Long', 'Hà Nội', '0123456789', 'admin.png', 1),
  (2, 'user1@bookstore.com', 'u123', 'Đạo hữu 1', 'Hồ Chí Minh', '0987654321', 'user1.png', 2),
  (3, 'user2@bookstore.com', 'u123', 'Đạo hữu 2', 'Đà Nẵng', '0911222333', 'user2.png', 2);