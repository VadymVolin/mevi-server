create database if not exists skillsapp;

use skillsapp;

create table if not exists user (
    id int auto_increment unsigned,
    primary key (id)
)

create table if not exists user_card {
    id int auto_increment unsigned,
    primary key (id)
}

create table if not exists skill {
    id int auto_increment unsigned,
    primary key (id)
}