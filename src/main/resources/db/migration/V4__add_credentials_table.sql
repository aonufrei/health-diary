create table credentials
(
    id          int auto_increment
        primary key,
    username    varchar(250) not null,
    password    varchar(250) not null,

    authority   int          not null,
    person_id   int          not null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

alter table credentials
    add constraint credentials_to_persons
        foreign key (person_id) references persons (id) on delete cascade on update cascade;