-- data population sql statements or data related filed

-- creating users
INSERT INTO users (username, password, enabled) VALUES ('user', '123456', true);

INSERT INTO users (username, password, enabled) VALUES ('admin', '123456', true);


-- creating authorities
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');

INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');