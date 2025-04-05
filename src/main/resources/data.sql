-- Insert sample users
INSERT INTO users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@example.com', 'ADMIN'),
('user1', 'password1', 'user1@example.com', 'USER'),
('user2', 'password2', 'user2@example.com', 'USER'),
('manager', 'manager123', 'manager@example.com', 'MANAGER');

-- Insert sample products
INSERT INTO products (name, category, price, description) VALUES
('Laptop', 'Electronics', 999.99, 'High-performance laptop'),
('Smartphone', 'Electronics', 599.99, 'Latest smartphone model'),
('Headphones', 'Electronics', 149.99, 'Noise-cancelling headphones'),
('Coffee Maker', 'Appliances', 79.99, 'Automatic coffee maker'),
('Blender', 'Appliances', 49.99, 'High-speed blender'),
('T-shirt', 'Clothing', 19.99, 'Cotton t-shirt'),
('Jeans', 'Clothing', 39.99, 'Denim jeans');
