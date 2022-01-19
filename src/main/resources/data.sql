CREATE TABLE IF NOT EXISTS user_login(
	user_id varchar(100) primary key,
    password varchar(200) not null,
    user_type enum("ADMIN","BUYER")
);

CREATE TABLE IF NOT EXISTS medicine_details(
	medicine_id int primary key auto_increment, 
    medicine_name varchar(200) not null, 
    price double not null, 
    min_age int default 0, 
    max_age int default 0
);

CREATE TABLE IF NOT EXISTS medicine_disease_map(
	entry_id int primary key auto_increment,
    medicine_id int not null,
    disease_name varchar(200) not null,
    CONSTRAINT unique_constraint UNIQUE(medicine_id,disease_name),
    constraint medicine_id_fkey foreign key (medicine_id) references medicine_details(medicine_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS medicine_disease_rating(
    rating_id int PRIMARY key AUTO_INCREMENT,
	user_id varchar(100) not null,
    entry_id int not null,
    rating double not null,
    CONSTRAINT unique_constraint UNIQUE(user_id,entry_id),
    constraint user_id_fkey foreign key (user_id) references user_login(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint entry_id_fkey foreign key (entry_id) references medicine_disease_map(entry_id) ON DELETE CASCADE ON UPDATE CASCADE
);