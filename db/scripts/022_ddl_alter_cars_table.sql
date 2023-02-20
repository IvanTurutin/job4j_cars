ALTER TABLE cars DROP COLUMN IF EXISTS name;
ALTER TABLE cars ADD COLUMN IF NOT EXISTS body_id int not null REFERENCES bodies(id);
ALTER TABLE cars ADD COLUMN IF NOT EXISTS car_model_id int not null REFERENCES car_models(id);
ALTER TABLE cars ADD COLUMN IF NOT EXISTS transmission_id int not null REFERENCES transmissions(id);

