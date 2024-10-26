create table if not exists users
(
    id  bigint primary key,
    first_name   varchar(128) not null,
    last_name varchar(128),
    email varchar(500) not null,
    dob timestamp not null,
    CONSTRAINT email UNIQUE (email)
);


CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
