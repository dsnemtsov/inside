INSERT INTO users(name, password)
VALUES ('user', 'password'),
       ('admin', 'password')
ON CONFLICT DO NOTHING;

INSERT INTO posts(message, user_id)
VALUES ('message 1', 1),
       ('message 2', 1),
       ('message 3', 1),
       ('message 4', 1),
       ('message 5', 1),
       ('message 6', 1),
       ('message 7', 1),
       ('message 8', 1),
       ('message 9', 1),
       ('message 10', 1),
       ('message 11', 1),
       ('message 12', 1)
ON CONFLICT DO NOTHING;