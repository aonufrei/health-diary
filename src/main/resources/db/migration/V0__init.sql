create table aims
(
    id            int auto_increment
        primary key,
    target_weight float       not null,
    status        int         not null,
    person_id     int         not null,
    mentioned     date        not null,
    modified_at   datetime(6) not null
);

create table body_reports
(
    id          int auto_increment
        primary key,
    value       float       not null,
    type        int         not null,
    person_id   int         not null,
    logged_time datetime(6) not null,
    created_at  datetime(6) null,
    modified_at datetime(6) null
);

create table food
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

create table food_reports
(
    id            int auto_increment
        primary key,
    amount        int         not null,
    type          int         not null,
    food_id       int         not null,
    metric_id     int         not null,
    reported_date date        not null,
    modified_at   datetime(6) null
);

create table likes
(
    id          int auto_increment
        primary key,
    person_id   int         not null,
    post_id     int         not null,
    created_at  datetime(6) null,
    modified_at datetime(6) null
);

create table metrics
(
    id          int auto_increment
        primary key,
    type        int         not null,
    calories    float       not null,
    carbs       float       null,
    protein     float       null,
    fat         float       null,
    food_id     int         not null,
    created_at  datetime(6) null,
    modified_at datetime(6) null
);

create table persons
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    dob         date         not null,
    email       varchar(255) null,
    image_path  varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

create table posts
(
    id          int auto_increment
        primary key,
    content     varchar(255) not null,
    author_id   int          not null,
    image_path  varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);


alter table aims
    add constraint aims_to_persons
        foreign key (person_id) references persons (id) on delete cascade on update cascade;

alter table body_reports
    add constraint body_reports_to_persons
        foreign key (person_id) references persons (id) on delete cascade on update cascade;

alter table food_reports
    add constraint food_reports_to_food
        foreign key (food_id) references food (id) on delete cascade on update cascade;

alter table food_reports
    add constraint food_reports_to_metric
        foreign key (metric_id) references metrics (id) on delete cascade on update cascade;

alter table likes
    add constraint likes_to_persons
        foreign key (person_id) references persons (id) on delete cascade on update cascade;

alter table likes
    add constraint likes_to_posts
        foreign key (post_id) references posts (id) on delete cascade on update cascade;

alter table metrics
    add constraint metrics_to_food
        foreign key (food_id) references food (id) on delete cascade on update cascade;


alter table posts
    add constraint posts_to_persons
        foreign key (author_id) references persons (id) on delete cascade on update cascade;