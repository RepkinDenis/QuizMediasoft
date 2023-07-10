create table category (
    id  bigserial not null,
    name varchar(255) not null,
    primary key (id)
);
create table question (
    id  bigserial not null,
    answer varchar(255) not null,
    difficulty int4 not null,
    question varchar(255) not null,
    category_id int8,
    primary key (id)
    foreign key (category_id) references category(id)
);


