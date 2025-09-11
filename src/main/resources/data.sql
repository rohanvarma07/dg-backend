-- Delete existing data
DELETE FROM products;
DELETE FROM categories;
DELETE FROM users;

-- Reset auto-increment counters
ALTER TABLE products AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

-- Insert categories
INSERT INTO categories (category_name, category_description) VALUES
('Car Wash Products', 'Products for washing and cleaning vehicles including soaps, shampoos, and cleaners'),
('Detailing Tools', 'Tools and equipment for professional car detailing and maintenance'),
('Protection Products', 'Wax, sealants, and protective coatings for vehicle surfaces'),
('Interior Care', 'Products for cleaning and maintaining vehicle interiors');

-- Insert products with category references
INSERT INTO products (prod_name, prod_price, prod_quantity, prod_description, img_url, category_id) VALUES
-- Car Wash Products (Category 1)
('Ceramic Car Wash Soap', 750, 100, 'Premium pH-balanced ceramic soap for a gentle, effective clean with long-lasting protection.', '/uploads/ceramiccarwash.png', 1),
('Bug & Tar Remover', 950, 60, 'Safely dissolves stubborn bug splatter and tar spots without damaging paint.', '/uploads/bugtar.png', 1),
('Premium Car Shampoo', 850, 75, 'Rich foaming shampoo that safely lifts dirt and grime while protecting your paint finish.', '/uploads/carshampoo.png', 1),
('Waterless Wash & Wax', 1200, 45, 'Eco-friendly waterless formula that cleans and waxes in one step.', '/uploads/waterlesswash.png', 1),
('Iron Fallout Remover', 1150, 35, 'Professional-grade iron contamination remover that changes color as it works.', '/uploads/ironremover.png', 1),

-- Detailing Tools (Category 2)
('Detailing Brushes Set', 2599, 45, 'Professional set of various brushes for intricate cleaning of vents, emblems, and tight spaces.', '/uploads/brushkit.png', 2),
('Microfiber Towel Pack', 1599, 80, 'Ultra-soft microfiber towels in various sizes for drying and detailing.', '/uploads/microfiber.png', 2),
('Foam Cannon', 3250, 25, 'Professional foam cannon for thick, clinging wash foam application.', '/uploads/foamcannon.png', 2),
('Detailing Clay Bar Kit', 1750, 55, 'Complete clay bar system for removing bonded contaminants.', '/uploads/claybar.png', 2),
('Dual Action Polisher', 12500, 15, 'Professional dual-action polisher for safe paint correction.', '/uploads/polisher.png', 2),

-- Protection Products (Category 3)
('Ceramic Coating 9H', 4500, 20, 'Professional-grade ceramic coating providing 2+ years of protection.', '/uploads/ceramic9h.png', 3),
('Carnauba Wax Paste', 2250, 35, 'Premium carnauba wax for deep gloss and protection.', '/uploads/carnaubawax.png', 3),
('Paint Sealant', 1850, 50, 'Synthetic sealant offering 6 months of durable protection.', '/uploads/paintsealant.png', 3),
('Quick Detailer Spray', 950, 70, 'Convenient spray for quick touch-ups and added gloss between washes.', '/uploads/quickdetailer.png', 3),

-- Interior Care (Category 4)
('Leather Conditioner', 1450, 60, 'Nourishing conditioner that keeps leather soft and prevents cracking.', '/uploads/leatherconditioner.png', 4),
('Fabric Protector', 1250, 45, 'Advanced protection against stains and spills for fabric surfaces.', '/uploads/fabricprotector.png', 4),
('Dashboard Protectant', 850, 85, 'UV protection for dashboard and interior plastic surfaces.', '/uploads/dashboard.png', 4),
('Interior Detailing Kit', 2850, 30, 'Complete kit with cleaners and protectants for all interior surfaces.', '/uploads/interiorkit.png', 4),
('Odor Eliminator', 1150, 65, 'Professional-strength odor neutralizer for lasting freshness.', '/uploads/odoreliminator.png', 4);

-- Sample login credentials for testing:
-- Email: admin@example.com, Password: admin123
-- Email: user@example.com, Password: user123
-- Email: test@example.com, Password: test123
