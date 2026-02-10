INSERT INTO city (name, center_lat, center_lng) VALUES
  ('Milano', 45.4642, 9.19),
  ('London', 51.5074, -0.1278),
  ('Paris', 48.8566, 2.3522),
  ('Berlin', 52.52, 13.405)
ON CONFLICT (name) DO NOTHING;

-- Milano
INSERT INTO place (external_id, city_name, category, name, description, lat, lng) VALUES
  ('milano-mxp-airport', 'Milano', 'STARTING_POINT', 'Malpensa Airport', 'Primary airport terminal for arrivals.', 45.63, 8.7231),
  ('milano-bus-terminal', 'Milano', 'STARTING_POINT', 'Lampugnano Bus Station', 'Intercity bus terminal with metro link.', 45.4869, 9.1245),
  ('milano-central-station', 'Milano', 'STARTING_POINT', 'Central Station', 'Main rail hub with easy transit access.', 45.4864, 9.2056),
  ('milano-porta-garibaldi', 'Milano', 'STARTING_POINT', 'Porta Garibaldi', 'Modern district with skyline views.', 45.4848, 9.1873),
  ('milano-duomo', 'Milano', 'ATTRACTION', 'Duomo Cathedral', 'Gothic landmark and panoramic rooftop views.', 45.4642, 9.1916),
  ('milano-sforza', 'Milano', 'ATTRACTION', 'Sforza Castle', 'Historic fortress with courtyards and museums.', 45.4699, 9.1791),
  ('milano-navigli', 'Milano', 'ATTRACTION', 'Navigli Canals', 'Canal-side walks and sunset aperitivo spots.', 45.4532, 9.1746),
  ('milano-brera', 'Milano', 'ATTRACTION', 'Brera Art Walk', 'Gallery district with boutiques and cafes.', 45.4714, 9.1884),
  ('milano-trattoria-rosa', 'Milano', 'RESTAURANT', 'Trattoria Rosa', 'Classic Milanese plates and fresh pasta.', 45.4669, 9.1872),
  ('milano-caffe-centrale', 'Milano', 'RESTAURANT', 'Caffe Centrale', 'Espresso bar with pastries and people watching.', 45.4686, 9.1938),
  ('milano-osteria-verde', 'Milano', 'RESTAURANT', 'Osteria Verde', 'Seasonal menu with regional flavors.', 45.4581, 9.1861),
  ('milano-panificio', 'Milano', 'RESTAURANT', 'Panificio Milano', 'Bakery with focaccia and morning bites.', 45.4635, 9.1815),
  ('milano-duomo-boutique', 'Milano', 'ACCOMMODATION', 'Duomo Boutique Hotel', 'Elegant rooms steps from the cathedral.', 45.4655, 9.1902),
  ('milano-navigli-suites', 'Milano', 'ACCOMMODATION', 'Navigli Suites', 'Canal-side suites with modern interiors.', 45.4524, 9.1772),
  ('milano-brera-loft', 'Milano', 'ACCOMMODATION', 'Brera Loft Stay', 'Designer loft in the arts district.', 45.4722, 9.1868),
  ('milano-porta-nuova', 'Milano', 'ACCOMMODATION', 'Porta Nuova Residence', 'High-rise stay with skyline views.', 45.4843, 9.1966)
ON CONFLICT (city_name, category, external_id) DO NOTHING;

-- London
INSERT INTO place (external_id, city_name, category, name, description, lat, lng) VALUES
  ('london-lhr-airport', 'London', 'STARTING_POINT', 'Heathrow Airport', 'Major international arrival hub.', 51.47, -0.4543),
  ('london-bus-victoria', 'London', 'STARTING_POINT', 'Victoria Coach Station', 'Central bus terminal for regional routes.', 51.4934, -0.1481),
  ('london-kings-cross', 'London', 'STARTING_POINT', 'King''s Cross', 'Major rail hub with quick underground access.', 51.5308, -0.1238),
  ('london-southbank', 'London', 'STARTING_POINT', 'Southbank', 'Riverside starting point near landmarks.', 51.5055, -0.109),
  ('london-westminster', 'London', 'ATTRACTION', 'Westminster Walk', 'Iconic riverfront landmarks and historic views.', 51.5008, -0.1247),
  ('london-soho', 'London', 'ATTRACTION', 'Soho Quarter', 'Creative streets with theaters and music venues.', 51.5136, -0.1365),
  ('london-skygarden', 'London', 'ATTRACTION', 'Sky Garden', 'Indoor garden with skyline perspectives.', 51.5107, -0.0837),
  ('london-hyde-park', 'London', 'ATTRACTION', 'Hyde Park', 'Lakeside paths and open green lawns.', 51.5073, -0.1657),
  ('london-borough-bites', 'London', 'RESTAURANT', 'Borough Bites', 'Market stalls and global street food.', 51.5054, -0.0914),
  ('london-thames-cafe', 'London', 'RESTAURANT', 'Thames Cafe', 'Riverside brunch with artisan coffee.', 51.5079, -0.0871),
  ('london-notting-hill', 'London', 'RESTAURANT', 'Notting Hill Kitchen', 'Neighborhood dining with seasonal dishes.', 51.5097, -0.2041),
  ('london-tea-room', 'London', 'RESTAURANT', 'Kensington Tea Room', 'Afternoon tea and fresh pastries.', 51.5022, -0.1875),
  ('london-southbank', 'London', 'ACCOMMODATION', 'Southbank Suites', 'Modern rooms near the river walk.', 51.5065, -0.1083),
  ('london-covent', 'London', 'ACCOMMODATION', 'Covent Garden Hotel', 'Boutique stay close to theater rows.', 51.5129, -0.1232),
  ('london-shoreditch', 'London', 'ACCOMMODATION', 'Shoreditch Loft', 'Industrial-chic stay with city views.', 51.526, -0.0786),
  ('london-paddington', 'London', 'ACCOMMODATION', 'Paddington Lodge', 'Convenient base near transport links.', 51.5157, -0.1752)
ON CONFLICT (city_name, category, external_id) DO NOTHING;

-- Paris
INSERT INTO place (external_id, city_name, category, name, description, lat, lng) VALUES
  ('paris-cdg-airport', 'Paris', 'STARTING_POINT', 'Charles de Gaulle Airport', 'International airport arrivals hub.', 49.0097, 2.5479),
  ('paris-bus-bercy', 'Paris', 'STARTING_POINT', 'Bercy Bus Station', 'Coach terminal for regional travel.', 48.8405, 2.379),
  ('paris-gare-du-nord', 'Paris', 'STARTING_POINT', 'Gare du Nord', 'Major station with metro connections.', 48.8809, 2.3553),
  ('paris-chatelet', 'Paris', 'STARTING_POINT', 'Chatelet', 'Central hub with access to many lines.', 48.858, 2.347),
  ('paris-louvre', 'Paris', 'ATTRACTION', 'Louvre District', 'Museum landmarks and riverside promenades.', 48.8606, 2.3376),
  ('paris-montmartre', 'Paris', 'ATTRACTION', 'Montmartre Hill', 'Artisan streets and scenic viewpoints.', 48.8867, 2.3431),
  ('paris-seine', 'Paris', 'ATTRACTION', 'Seine River Stroll', 'Bridges, bookstalls, and evening glow.', 48.8567, 2.3528),
  ('paris-marais', 'Paris', 'ATTRACTION', 'Le Marais', 'Historic lanes with boutique shopping.', 48.8589, 2.3622),
  ('paris-bistro-bleu', 'Paris', 'RESTAURANT', 'Bistro Bleu', 'Classic bistro fare with terrace seating.', 48.8644, 2.3335),
  ('paris-patisserie', 'Paris', 'RESTAURANT', 'Rive Gauche Patisserie', 'Croissants, tarts, and espresso.', 48.8514, 2.3406),
  ('paris-market', 'Paris', 'RESTAURANT', 'Marais Market Kitchen', 'Market-driven plates and small bites.', 48.8572, 2.3661),
  ('paris-seine-cafe', 'Paris', 'RESTAURANT', 'Seine Cafe', 'Light lunches with river views.', 48.8542, 2.3568),
  ('paris-opera', 'Paris', 'ACCOMMODATION', 'Opera Boutique Hotel', 'Elegant rooms near the grand boulevards.', 48.8719, 2.3322),
  ('paris-latin', 'Paris', 'ACCOMMODATION', 'Latin Quarter Stay', 'Quiet streets with cafes nearby.', 48.8499, 2.347),
  ('paris-marais-loft', 'Paris', 'ACCOMMODATION', 'Marais Loft Residence', 'Stylish apartments in the heart of Marais.', 48.8597, 2.3591),
  ('paris-etoile', 'Paris', 'ACCOMMODATION', 'Etoile Suites', 'Refined stay close to Arc de Triomphe.', 48.8738, 2.295)
ON CONFLICT (city_name, category, external_id) DO NOTHING;

-- Berlin
INSERT INTO place (external_id, city_name, category, name, description, lat, lng) VALUES
  ('berlin-ber-airport', 'Berlin', 'STARTING_POINT', 'Berlin Brandenburg Airport', 'Main airport for arrivals and departures.', 52.3667, 13.5033),
  ('berlin-bus-zob', 'Berlin', 'STARTING_POINT', 'ZOB Central Bus Station', 'Intercity coach terminal in the west.', 52.507, 13.2797),
  ('berlin-hbf', 'Berlin', 'STARTING_POINT', 'Berlin Hbf', 'Central station with city connections.', 52.5251, 13.3694),
  ('berlin-alexanderplatz', 'Berlin', 'STARTING_POINT', 'Alexanderplatz', 'Major square with S-Bahn and U-Bahn.', 52.5219, 13.4132),
  ('berlin-brandenburg', 'Berlin', 'ATTRACTION', 'Brandenburg Gate', 'Historic square and iconic city landmark.', 52.5163, 13.3777),
  ('berlin-museum-island', 'Berlin', 'ATTRACTION', 'Museum Island', 'Cluster of museums by the river.', 52.5169, 13.401),
  ('berlin-east-side', 'Berlin', 'ATTRACTION', 'East Side Gallery', 'Open-air art on the Berlin Wall.', 52.5053, 13.4396),
  ('berlin-tiergarten', 'Berlin', 'ATTRACTION', 'Tiergarten Park', 'Central park with lakes and trails.', 52.5145, 13.3501),
  ('berlin-markt-halle', 'Berlin', 'RESTAURANT', 'Markt Halle Kitchen', 'Food hall with global flavors.', 52.5005, 13.4317),
  ('berlin-mitte-cafe', 'Berlin', 'RESTAURANT', 'Mitte Cafe', 'Minimalist cafe with brunch plates.', 52.5208, 13.4055),
  ('berlin-kreuzberg', 'Berlin', 'RESTAURANT', 'Kreuzberg Eats', 'Creative dining in lively streets.', 52.4986, 13.4036),
  ('berlin-bakery', 'Berlin', 'RESTAURANT', 'Spree Bakery', 'Fresh bread and morning pastries.', 52.5175, 13.3867),
  ('berlin-mitte-hotel', 'Berlin', 'ACCOMMODATION', 'Mitte Design Hotel', 'Modern stay with easy transit access.', 52.5221, 13.4017),
  ('berlin-charlottenburg', 'Berlin', 'ACCOMMODATION', 'Charlottenburg Suites', 'Spacious suites near shopping streets.', 52.5167, 13.3041),
  ('berlin-friedrichshain', 'Berlin', 'ACCOMMODATION', 'Friedrichshain Loft', 'Loft-style stay in a creative district.', 52.5152, 13.454),
  ('berlin-spree', 'Berlin', 'ACCOMMODATION', 'Spree Riverside Lodge', 'Calm rooms by the river.', 52.5078, 13.3896)
ON CONFLICT (city_name, category, external_id) DO NOTHING;
