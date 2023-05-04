CREATE TABLE IF NOT EXISTS engines
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

comment on table engines is 'Двигатели';
comment on column engines.id is 'Идентификатор двигателя';
comment on column engines.name is 'Название двигателя/модель/модификация';