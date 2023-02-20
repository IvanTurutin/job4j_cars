CREATE TABLE IF NOT EXISTS bodies
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

comment on table bodies is 'Кузова';
comment on column bodies.id is 'Идентификатор кузова';
comment on column bodies.name is 'Название кузова';
