CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY ,
    name     VARCHAR(30),
    password VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS posts
(
    id      SERIAL PRIMARY KEY ,
    message VARCHAR(255),
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE  ON UPDATE CASCADE
);