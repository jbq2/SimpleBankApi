use simplebankapidb;

alter table user_role
drop foreign key user_role_ibfk_1;

alter table user_role
drop foreign key user_role_ibfk_2;

alter table user_role
add constraint user_role_ibfk_1
foreign key (user_id)
references users(id)
on delete cascade on update cascade;

alter table user_role
add constraint user_role_ibfk_2
foreign key(role_id)
references roles(id)
on delete cascade on update cascade;