CREATE TABLE IF NOT EXISTS transmissions
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

comment on table transmissions is 'Коробки передач';
comment on column transmissions.id is 'Идентификатор коробки передач';
comment on column transmissions.name is 'Название коробки передач';
