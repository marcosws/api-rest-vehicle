insert into user_tab(name, login, password) values('Admin','admin','$2a$10$bTiY2gJ1zQf9NuGLueISReN0Ub0PiDt5pXsHrmShArHimkcixd1ES');
insert into user_tab(name, login, password) values('User','user','$2a$10$VDI4TSnBWaPCg2.Ua4ov1uLPiX76PGtzTog96WeawFxka2ytu.z2e');

insert into role_tab(name) values('ROLE_ADMIN');
insert into role_tab(name) values('ROLE_USER');

insert into user_roles(user_id,role_id) values(1,1);
insert into user_roles(user_id,role_id) values(1,2);
insert into user_roles(user_id,role_id) values(2,2);