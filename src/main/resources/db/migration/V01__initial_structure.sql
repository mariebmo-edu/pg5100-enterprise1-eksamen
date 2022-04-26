create table users(
    user_id bigserial primary key,
    user_email varchar(50),
    user_password varchar(255),
    user_created timestamp,
    user_enabled bool
);

create table authorities(
    authority_id bigserial primary key,
    authority_name varchar(50)
);

create table users_authorities(
    authority_id bigint,
    user_id bigint
);

create table status(
    status_id bigserial primary key,
    status_name varchar(100)
);

create table animals(
    animal_id bigserial primary key,
    animal_name varchar(50),
    animal_breed varchar(50),
    animal_age int,
    animal_health varchar(500),
    animal_created timestamp,
    status_id bigint not null references status(status_id)
)