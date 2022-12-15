CREATE TABLE IF NOT EXISTS auto_users
(
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL
);

comment on table auto_users is 'Пользователи сайта';
comment on column auto_users.id is 'Идентификатор пользователя';
comment on column auto_users.login is 'Логин пользователя';
comment on column auto_users.password is 'Пароль пользователя';
