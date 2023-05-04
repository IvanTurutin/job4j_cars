CREATE TABLE IF NOT EXISTS auto_posts
(
    id SERIAL PRIMARY KEY,
    text VARCHAR NOT NULL,
    publish BOOLEAN NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    auto_user_id INT NOT NULL REFERENCES auto_users(id)
);

comment on table auto_posts is 'Объявления о продаемом авто';
comment on column auto_posts.id is 'Идентификатор объявления';
comment on column auto_posts.text is 'Текст объявления';
comment on column auto_posts.publish is 'Объявление закрыто?';
comment on column auto_posts.created is 'Дата создания объявления';
comment on column auto_posts.auto_user_id is 'Идентификатор пользователя, который создал объявление';
