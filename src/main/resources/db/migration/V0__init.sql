create table aims
(
    id            int         not null
        primary key,
    status        int         null,
    target_weight float       not null,
    person_id     int         null,
    mentioned     date        null,
    modified_at   datetime(6) null
);

create table food
(
    id          int          not null
        primary key,
    name        varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

create table food_reports
(
    id            int         not null
        primary key,
    food_id       int         null,
    amount        int         null,
    metric_id     int         null,
    reported_date date        null,
    modified_at   datetime(6) null

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
    name        int         null,
    value       int         null,
    food_id     int         null,
    created_at  datetime(6) null,
    modified_at datetime(6) null
);

create table persons
(
    id          int          not null
        primary key,
    name        varchar(255) null,
    email       varchar(255) null,
    image_path  varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

create table posts
(
    id          int          not null
        primary key,
    content     varchar(255) null,
    image_path  varchar(255) null,
    author_id   int          null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

alter table aims
    add constraint aim_to_person
        foreign key (person_id) references persons (id);

alter table food_reports
    add constraint food_report_to_food
        foreign key (food_id) references food (id);

alter table food_reports
    add constraint food_report_to_metric
        foreign key (metric_id) references metrics (id);

alter table likes
    add constraint like_to_person
        foreign key (person_id) references persons (id);

alter table likes
    add constraint like_to_post
        foreign key (post_id) references posts (id);

alter table metrics
    add
        constraint metric_to_food
            foreign key (food_id) references food (id);

alter table posts
    add constraint post_to_person
        foreign key (author_id) references persons (id)


