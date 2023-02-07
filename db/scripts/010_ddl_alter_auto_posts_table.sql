ALTER TABLE auto_posts ADD COLUMN IF NOT EXISTS car_id int not null REFERENCES cars(id);
