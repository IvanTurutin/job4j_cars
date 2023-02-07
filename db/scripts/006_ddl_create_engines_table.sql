CREATE TABLE IF NOT EXISTS engines
(
    id SERIAL PRIMARY KEY
);

comment on table engines is 'Двигатели';
comment on column engines.id is 'Идентификатор двигателя';