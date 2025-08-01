create table users (
    id uuid not null primary key,
    name varchar(75) not null,
    email varchar(150) not null unique
);
