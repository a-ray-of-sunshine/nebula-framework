drop table if exists sys_user;

create table sys_user(
    id bigint generated by default as identity,
    username varchar(255),
    password varchar(255),
    primary key (id)
);