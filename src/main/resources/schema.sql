-- file to set up schema in spring boot
-- not good in production
-- default Spring Security user schema
-- find reference here https://docs.spring.io/spring-security/reference/servlet/appendix/database-schema.html


create table users(
                      username varchar_ignorecase(50) not null primary key,
                      password varchar_ignorecase(50) not null,
                      enabled boolean not null
);

create table authorities (
                             username varchar_ignorecase(50) not null,
                             authority varchar_ignorecase(50) not null,
                             constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);