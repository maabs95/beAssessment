CREATE TABLE IF NOT EXISTS book (
    id varchar(255) PRIMARY KEY,
    isbn varchar(255) not null,
    title varchar(255) not null,
    author varchar(255) not null
);

CREATE TABLE IF NOT EXISTS borrower (
    userId varchar(255) PRIMARY KEY,
    name varchar(255) not null,
    email varchar(255) not null
);

CREATE TABLE IF NOT EXISTS borrowedBook (
    id varchar(255) PRIMARY KEY,
    bookId varchar(255) not null,
    borrowerId varchar(255) not null,
    returned varchar(1) default 'N'
);