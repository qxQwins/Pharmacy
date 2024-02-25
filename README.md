db script: 
CREATE DATABASE IF NOT EXISTS pharmacy; 
use pharmacy;
create table companies
(
    company_id   int auto_increment
        primary key,
    company_name varchar(255) null,
    address      varchar(255) null
);

create table products
(
    products_id  int auto_increment
        primary key,
    product_name varchar(255) null,
    price        int          null,
    company      int          null,
    constraint products_companies_company_id_fk
        foreign key (company) references companies (company_id)
);

create table users
(
    user_id  int auto_increment
        primary key,
    login    varchar(255) null,
    password varchar(255) null
);

