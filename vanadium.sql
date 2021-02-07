show databases;
use vanadium;

drop table players;
create table players(id int auto_increment primary key not null,name char(16) not null,heart int default 10 not null,regen int default 0 not null,money int default 0 not null);
insert into players(name) value("nicofighter45");
select * from players;
delete from players;
update players set money = 10 where id = 1;

drop table shop;
create table shop(id int auto_increment primary key not null,itemid int not null,sell float not null,buy float not null,stock int);
insert into shop(itemid,sell,buy,stock) value(1,1.0,10.0,5);
select * from shop;
delete from shop;
update shop set sell = 5.0 where itemid = 1;