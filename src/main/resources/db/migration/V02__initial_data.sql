insert into users
values(nextval('users_user_id_seq'), 'super@admin.com', '$2a$12$LBmxx8.3.r7MhPtzVYAAI.wKLR.5QFxQHrzLdokuN2nHGFNeH8TGi', now(), true);

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into users_authorities
values (1, 1);

insert into users_authorities
values (1, 2);

insert into status
values (nextval('status_status_id_seq'), 'ADOPTED');

insert into status
values (nextval('status_status_id_seq'), 'DEAD');

insert into status
values (nextval('status_status_id_seq'), 'TEST WEEK');

insert into status
values (nextval('status_status_id_seq'), 'AWAITING HEALTH CHECK');


insert into animals
values(nextval('animals_animal_id_seq'), 'Bob', 'Dog', 15, 'Got worms', now(), 4);

insert into animals
values(nextval('animals_animal_id_seq'), 'Geir', 'Horse', 24, 'Could be better', now(), 2);

insert into animals
values(nextval('animals_animal_id_seq'), 'Karen', 'Cow', 8, 'Prime meat', now(), 3);