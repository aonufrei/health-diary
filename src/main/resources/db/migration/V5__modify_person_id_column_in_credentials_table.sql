set mode mysql;

alter table credentials
    modify column person_id INT null;