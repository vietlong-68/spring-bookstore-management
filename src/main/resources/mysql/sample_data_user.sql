INSERT INTO roles (id, name, description) VALUES
  (1, 'ADMIN', 'Quản trị viên'),
  (2, 'USER', 'Khách hàng thông thường');

INSERT INTO users (email, password, full_name, address, phone, avatar, role_id) VALUES
  ('admin@bookstore.com', '$2a$10$JErreXBeYiW4uLWf7QtFwuUc68MpuKeOymVGn2YjLAUoh2Px76C1C', 'Việt Long', 'Hà Nội', '0909123456', '/images/avatar/user_male.png', 1),
  ('user1@bookstore.com', '$2a$10$JErreXBeYiW4uLWf7QtFwuUc68MpuKeOymVGn2YjLAUoh2Px76C1C', 'Nguyễn Thị A', 'Đà Nẵng', '0911222333', '/images/avatar/user_female.png', 2);
