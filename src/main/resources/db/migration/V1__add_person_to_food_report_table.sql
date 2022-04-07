alter table food_reports add column person_id int not null after type,
                         add constraint food_reports_to_person
                             foreign key (person_id) references persons (id) on delete cascade on update cascade;