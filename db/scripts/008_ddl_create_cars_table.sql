CREATE TABLE IF NOT EXISTS cars
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    engine_id INT NOT NULL UNIQUE REFERENCES engines(id),
    owner_id INT NOT NULL REFERENCES owners(id)
);

comment on table cars is 'Автомобили';
comment on column cars.id is 'Идентификатор автомобиля';
comment on column cars.name is 'Название автомобиля';
comment on column cars.engine_id is 'Идентификатор двигателя, установленного в автомобиле';
comment on column cars.owner_id is 'Идентификатор текущего владельца автомобиля';
