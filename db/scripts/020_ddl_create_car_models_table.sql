CREATE TABLE IF NOT EXISTS car_models
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

comment on table car_models is 'Марки';
comment on column car_models.id is 'Идентификатор марки';
comment on column car_models.name is 'Название марки';
