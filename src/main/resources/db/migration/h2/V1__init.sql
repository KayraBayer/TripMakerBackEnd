CREATE TABLE IF NOT EXISTS city (
  name VARCHAR(255) PRIMARY KEY,
  center_lat DOUBLE PRECISION NOT NULL,
  center_lng DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS place (
  pk BIGINT AUTO_INCREMENT PRIMARY KEY,
  external_id VARCHAR(255) NOT NULL,
  city_name VARCHAR(255) NOT NULL,
  category VARCHAR(64) NOT NULL,
  name VARCHAR(512) NOT NULL,
  description VARCHAR(2048) NOT NULL,
  lat DOUBLE PRECISION NOT NULL,
  lng DOUBLE PRECISION NOT NULL,
  food_type VARCHAR(255) NULL,
  accommodation_type VARCHAR(255) NULL,
  attraction_type VARCHAR(255) NULL,
  history_summary CLOB NULL,
  stars DOUBLE PRECISION NULL,
  price_range VARCHAR(255) NULL,
  image_urls CLOB NULL,
  reviews CLOB NULL,
  location CLOB NULL,
  price_comparisons CLOB NULL,
  embedding CLOB NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_place_city FOREIGN KEY (city_name) REFERENCES city(name) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS uidx_place_city_category_external_id
  ON place(city_name, category, external_id);

CREATE INDEX IF NOT EXISTS idx_place_city_category ON place(city_name, category);

CREATE TABLE IF NOT EXISTS route_request (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  city_name VARCHAR(255) NOT NULL,
  starting_point_id VARCHAR(255) NULL,
  starting_point CLOB NULL,
  prompt CLOB NULL,
  selections CLOB NULL,
  selected_attraction_ids CLOB NULL,
  selected_restaurant_ids CLOB NULL,
  selected_accommodation_ids CLOB NULL
);

