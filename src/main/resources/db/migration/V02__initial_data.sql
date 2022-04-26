insert into users
values(nextval('users_user_id_seq'), 'super@admin.com', '$2a$12$derD79yQbp6e/GaSiBOS/uqbdoKj6oYqTUAZC.VAcd6Yp.dom0zm6', now(), true);

insert into users
values(nextval('users_user_id_seq'), 'ordinary@employee.com', '$2a$12$4i4d3AlZ41cSKoWqNzOYqOXCTfWblBwWhBe479a2ZC4zw5pVuzgU6', now(), true);

insert into users
values(nextval('users_user_id_seq'), 'normal@user.com', '$2a$12$MW7yn7WkLb/4hXMOylEHBeLhYN7/xZ.TZGhstTCvOjdqJIBXPVbZ.', now(), true);



insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'EMPLOYEE');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');


insert into users_authorities
values (1, 1);

insert into users_authorities
values (2, 1);

insert into users_authorities
values (3, 1);

insert into users_authorities
values (1, 2);

insert into users_authorities
values (2, 2);

insert into users_authorities
values (1, 3);



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