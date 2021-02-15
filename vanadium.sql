show databases;
use vanadium;

drop table players;
create table players(name char(16) not null,heart int default 10 not null,regen int default 0 not null,money int default 0 not null);
insert into players(name) value("nicofighter45");
select * from players;
delete from players;
update players set money = 10 where id = 1;

drop table shop;
create table shop(itemid int not null,sell float not null,buy float not null,stock int);
insert into shop(itemid,sell,buy,stock) value(1,1.0,10.0,500);
insert into shop(itemid,sell,buy,stock) value(2,1.0,10.0,100);
insert into shop(itemid,sell,buy,stock) value(3,1.0,10.0,1000);
select * from shop;
delete from shop;
update shop set sell = 5.0 where itemid = 1;