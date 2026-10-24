CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(130) NOT NULL CHECK (length(trim(full_name)) > 0),
    phone VARCHAR(30) NOT NULL UNIQUE CHECK (length(trim(phone)) > 0)
);

CREATE TABLE IF NOT EXISTS cars (
    id BIGSERIAL PRIMARY KEY,
    car_type VARCHAR(20) NOT NULL CHECK (car_type IN ('ECONOMY', 'LUXURY')),
    brand VARCHAR(60) NOT NULL CHECK (length(trim(brand)) > 0),
    model VARCHAR(60) NOT NULL CHECK (length(trim(model)) > 0),
    price_per_day NUMERIC(9, 2) NOT NULL CHECK (price_per_day > 0)
);

CREATE TABLE IF NOT EXISTS rentals (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    car_id BIGINT NOT NULL REFERENCES cars(id),
    days INT NOT NULL CHECK (days > 0)
);

CREATE INDEX IF NOT EXISTS rentals_customer_id_idx ON rentals(customer_id);
CREATE INDEX IF NOT EXISTS rentals_car_id_idx ON rentals(car_id);
