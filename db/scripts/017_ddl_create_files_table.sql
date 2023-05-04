CREATE TABLE IF NOT EXISTS files
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL,
    post_id INT REFERENCES auto_posts(id),
    UNIQUE(name, path)
);

comment on table files is 'Файлы';
comment on column files.id is 'Идентификатор файла';
comment on column files.name is 'Имя файла';
comment on column files.path is 'Путь к файлу';
comment on column files.post_id is 'Идентификатор объявления';
