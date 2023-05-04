CREATE TABLE IF NOT EXISTS owners
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

comment on table owners is 'Владельцы автомобилей';
comment on column owners.id is 'Идентификатор владельца';
comment on column owners.name is 'ФИО владельца';

