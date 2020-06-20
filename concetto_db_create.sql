create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
create table concept (id bigint not null auto_increment, date_created datetime, date_last_reviewed datetime, done bit not null, explanation varchar(255) not null, name varchar(100), reviewed bit not null, simplified bit not null, subject_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table subject (id bigint not null auto_increment, count integer not null, last_update datetime, name varchar(100) not null, review_count integer not null, user_id bigint not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, email varchar(255) not null, primary key (id)) engine=InnoDB
alter table concept add constraint FK1ar0wubh5j5p0bx4xwyvtqm7q foreign key (subject_id) references subject (id)
alter table concept add constraint FKt454go2gxfqyq9199gc4wxqlq foreign key (user_id) references user (id)
alter table subject add constraint FK96a455takm74cb3lol4571d7a foreign key (user_id) references user (id)
