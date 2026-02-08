CREATE TABLE IF NOT EXISTS customers (
                                         id BIGSERIAL PRIMARY KEY,
                                         full_name VARCHAR(120) NOT NULL,
                                         phone VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cars (
                                    id BIGSERIAL PRIMARY KEY,
                                    car_type VARCHAR(20) NOT NULL,   -- ECONOMY / LUXURY
                                    brand VARCHAR(60) NOT NULL,
                                    model VARCHAR(60) NOT NULL,
                                    price_per_day NUMERIC(10,2) NOT NULL CHECK (price_per_day > 0)
);


CREATE TABLE IF NOT EXISTS rentals (
                                       id BIGSERIAL PRIMARY KEY,
                                       customer_id BIGINT NOT NULL,
                                       car_id BIGINT NOT NULL,
                                       days INT NOT NULL CHECK (days > 0),

                                       CONSTRAINT fk_rentals_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
                                       CONSTRAINT fk_rentals_car FOREIGN KEY (car_id) REFERENCES cars(id)
);
