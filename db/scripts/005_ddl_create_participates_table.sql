CREATE TABLE IF NOT EXISTS participates (
   id serial PRIMARY KEY,
   post_id int not null REFERENCES auto_posts(id),
   user_id int not null REFERENCES auto_users(id)
);