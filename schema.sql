CREATE TABLE IF NOT EXISTS clothes_inventory (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    size VARCHAR(10) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    waist_size INT,
    is_long_sleeve BOOLEAN,
    fit_type VARCHAR(50),
    button_count INT,
    quantity INT DEFAULT 1
);