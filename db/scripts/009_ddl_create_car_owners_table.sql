CREATE TABLE IF NOT EXISTS car_owners
(
    id SERIAL PRIMARY KEY,
    car_id INT NOT NULL REFERENCES cars(id),
    owner_id INT NOT NULL REFERENCES owners(id),
    startat TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    endat TIMESTAMP WITHOUT TIME ZONE DEFAULT now()

);

comment on table car_owners is 'Владельцы автомобилей';
comment on column car_owners.id is 'Идентификатор записи';
comment on column car_owners.car_id is 'Идентификатор автомобиля';
comment on column car_owners.owner_id is 'Идентификатор владельца';
comment on column car_owners.startat is 'Дата начала владения автомобилем';
comment on column car_owners.endat is 'Дата окончания владения автомобилем';