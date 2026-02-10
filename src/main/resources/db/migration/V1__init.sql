CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS city (
  name TEXT PRIMARY KEY,
  center_lat DOUBLE PRECISION NOT NULL,
  center_lng DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS place (
  pk BIGSERIAL PRIMARY KEY,
  external_id TEXT NOT NULL,
  city_name TEXT NOT NULL REFERENCES city(name) ON DELETE CASCADE,
  category TEXT NOT NULL,
  name TEXT NOT NULL,
  description TEXT NOT NULL,
  lat DOUBLE PRECISION NOT NULL,
  lng DOUBLE PRECISION NOT NULL,
  food_type TEXT NULL,
  accommodation_type TEXT NULL,
  attraction_type TEXT NULL,
  history_summary TEXT NULL,
  stars DOUBLE PRECISION NULL,
  price_range TEXT NULL,
  image_urls JSONB NULL,
  reviews JSONB NULL,
  location JSONB NULL,
  price_comparisons JSONB NULL,
  embedding VECTOR(1536) NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX IF NOT EXISTS uidx_place_city_category_external_id
  ON place(city_name, category, external_id);

CREATE INDEX IF NOT EXISTS idx_place_city_category ON place(city_name, category);

CREATE TABLE IF NOT EXISTS route_request (
  id UUID PRIMARY KEY,
  created_at TIMESTAMPTZ NOT NULL,
  city_name TEXT NOT NULL,
  starting_point_id TEXT NULL,
  starting_point JSONB NULL,
  prompt TEXT NULL,
  selections JSONB NULL,
  selected_attraction_ids JSONB NULL,
  selected_restaurant_ids JSONB NULL,
  selected_accommodation_ids JSONB NULL
);
