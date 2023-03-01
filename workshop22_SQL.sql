# Workshop 22 - RSVP

use hotelreservation;

/*
create table rsvp (
	id int not null auto_increment,
    full_name varchar(150) not null,
    email varchar(15) not null,
    phone numeric(8),
    confirmation_date date,
    comments varchar(255),
    constraint pk_rsvp_id primary key (id)
);
*/

# GET /api/rsvps
select * from rsvp;

# GET /api/rsvp?q=fred
select * from rsvp where full_name like '%fred%';

# POST /api/rsvp
insert into rsvp (full_name, email, phone, confirmation_date, comments)
values ('Fred Perry', 'fred@gmail.com', 91234567, '2023-02-01', 'fred rsvp to 8 events');

# PUT /api/rsvp/{id}
update rsvp set full_name = 'fred', email = 'fred@g.com', phone = '99999999', confirmation_date = '2023-01-01', comments = 'fred rsvp - event 8' 
where id = 1;

# GET /api/rsvps/count
select count(*) as cnt from rsvp;

# SPAM MORE DATA
insert into rsvp (full_name, email, phone, confirmation_date, comments)
values ('Mango', 'mango@gmail.com', 00000000, '2021-02-01', 'NIL');

# UPSERT
INSERT IGNORE INTO rsvp (full_name, email, phone, confirmation_date, comments)  
VALUES ('Mango2', 'mango2@gmail.com', 00000000, '2021-02-01', 'NIL');