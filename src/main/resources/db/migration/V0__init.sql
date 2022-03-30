create table aims
(
    id            int         not null
        primary key,
    mentioned     date        null,
    modified_at   datetime(6) null,
    status        int         null,
    target_weight float       not null,
    person_id     int         null
);


create table food
(
    id          int          not null
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    name        varchar(255) null
);

create table food_reports
(
    id            int         not null
        primary key,
    modified_at   datetime(6) null,
    reported_date date        null,
    food_id       int         null

);



create table likes
(
    id        int not null
        primary key,
    person_id int null,
    post_id   int null
);



create table metrics
(
    id          int         not null
        primary key,
    created_at  datetime(6) null,
    modified_at datetime(6) null,
    name        int         null,
    value       int         null,
    food_id     int         null
);



create table persons
(
    id          int          not null
        primary key,
    created_at  datetime(6)  null,
    email       varchar(255) null,
    image_path  varchar(255) null,
    modified_at datetime(6)  null,
    name        varchar(255) null
);

create table posts
(
    id          int          not null
        primary key,
    content     varchar(255) null,
    created_at  datetime(6)  null,
    image_path  varchar(255) null,
    modified_at datetime(6)  null,
    author_id   int          null
);

alter table aims
    add constraint FKl2g1yesrvnktm58hphg2ydfj9
        foreign key (person_id) references persons (id);

alter table food_reports
    add constraint FKhbpk52qxr1cn8lmnalr1scagv
        foreign key (food_id) references food (id);

alter table likes
    add constraint FK2bgnjb3394d7a02rsqbmqbdg9
        foreign key (person_id) references persons (id);

alter table likes
    add constraint FKry8tnr4x2vwemv2bb0h5hyl0x
        foreign key (post_id) references posts (id);

alter table metrics
    add
        constraint FKraxq8a839mr2tlcutek1uu4ks
            foreign key (food_id) references food (id);

alter table posts
    add constraint FK31biid9u6ekl7h6n2i7fgnqdq
        foreign key (author_id) references persons (id)


