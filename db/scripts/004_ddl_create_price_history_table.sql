CREATE TABLE IF NOT EXISTS price_history
(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   post_id int REFERENCES auto_posts(id)
);

comment on table price_history is 'Объявления о продаемом авто';
comment on column price_history.id is 'Идентификатор изменения цены';
comment on column price_history.before is 'Цена до изменения';
comment on column price_history.after is 'Цена после изменения';
comment on column price_history.created is 'Дата изменения цены';
