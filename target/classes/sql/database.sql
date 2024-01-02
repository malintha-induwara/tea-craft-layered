CREATE DATABASE IF NOT EXISTS teacraft;

USE teacraft;

CREATE TABLE user(username varchar(35) primary key,password varchar(10) not null,email varchar(30) not null );

CREATE TABLE employee( empId varchar(10) primary key,firstName varchar(30) not null,lastName varchar(30) not null,address varchar(150) not null,sex varchar(10) not null,dob DATE not null,mobileNo varchar(15) not null);

CREATE TABLE attendance(
    attendanceId varchar(30) primary key,
    date date not null,
    empId varchar(10) not null,
    inTime time not null,
    outTime time ,
    isPayed BOOLEAN default false,
    foreign key (empId) references employee(empId) on update cascade on delete cascade);


CREATE TABLE salary (salaryId varchar(30) primary key,
                     empId varchar(10) not null,
                     amount double not null,
                    dateCount int not null,
                     date date not null,
                     foreign key (empId) references employee (empId) on update cascade on delete cascade);

CREATE TABLE tea_supplier( supId varchar(30) primary key,
                           firstName varchar(30) not null,
                           lastName varchar(30) not null,
                           address varchar(150) not null,
                           bank varchar(30) not null,
                           bankNo varchar(30) not null,
                           mobileNo varchar(30) not null);


CREATE TABLE fertilizer( fertilizerId varchar(30) primary key,
                         brand varchar(30) not null,
                        description varchar(30) not null,
                        size varchar(30) not null,
                         unitPrice double not null,
                         qty_on_hand int not null);

CREATE TABLE  fertilizer_orders(
                                   fertOid  varchar(30) primary key,
                                   supId varchar(30) not null,
                                   date date not null,
                                   foreign key (supId) references tea_supplier(supId) on update cascade on delete cascade);

CREATE TABLE  fertilizer_order_details(
                                          fertOid varchar(30) not null,
                                          fertilizerId varchar(30) not null,
                                          qty int not null,
                                          foreign key (fertOid) references  fertilizer_orders(fertOid) on update cascade on delete cascade,
                                          foreign key (fertilizerId) references  fertilizer(fertilizerId) on update cascade on delete cascade);

CREATE TABLE tea_leaves_stock(
                                 teaStockId varchar(30) primary key,
                                 supId varchar(30) not null,
                                 teaBookId varchar(30) not null,
                                 amount double not null,
                                 isPayed BOOLEAN default false,
                                 foreign key (supId) references tea_supplier(supId) on update cascade on delete cascade);

CREATE TABLE tea_book(
                         teaBookId varchar(30) primary key,
                         dailyAmount double not null,
                         date date);

ALTER TABLE tea_leaves_stock ADD CONSTRAINT fk_teaBook FOREIGN KEY (teaBookId) REFERENCES tea_book(teaBookId) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE tea_book_type_details(
                                      teaBookTypeId varchar(30) primary key,
                                      date date not null,
                                      typeId varchar(30) not null,
                                      amount double not null,
                                      isConfirmed BOOLEAN default false
                                      );

CREATE TABLE tea_types(
                          typeId varchar(30) primary key,
                          type varchar(30) not null,
                          amount double not null);

ALTER TABLE tea_book_type_details ADD CONSTRAINT fk_teaTypes FOREIGN KEY (typeId) REFERENCES  tea_types(typeId) ON UPDATE CASCADE ON DELETE CASCADE;


CREATE TABLE customer(
                         cusId varchar(30) primary key,
                         firstName varchar(30) not null,
                         lastName varchar(30) not null,
                         address varchar(150) not null,
                         email varchar(50) not null,
                         mobileNo varchar(30) not null);

CREATE TABLE tea_orders(
                           tea_order_id varchar(30) primary key,
                           cusId varchar(30) not null,
                           date date not null,
                           foreign key (cusId) references customer(cusId) on update cascade on delete cascade);


CREATE TABLE packaging(
                          packId varchar(30) primary key,
                          typeId varchar(30) not null,
                          description varchar(30) not null,
                          packageCount int not null,
                          price double not null,
                          foreign key (typeId) references tea_types(typeId) on update cascade on delete cascade
);


CREATE TABLE tea_order_details(
                                  tea_order_id varchar(30),
                                  packId varchar(30),
                                  qty int not null,
                                  foreign key (tea_order_id ) references tea_orders(tea_order_id) on update cascade on delete cascade,
                                  foreign key (packId ) references packaging(packId) on update cascade on delete cascade
);


create table payments(
     paymentId varchar(30) primary key,
     supId varchar(30) not null,
     amount double not null,
     payment double not null,
     date date
     foreign key (supId) references tea_supplier(supId) on update cascade on delete cascade


    );

create table packaging_details(
                packagingDetailsId varchar(30) primary key,
                date date not null,
                packId varchar(30) not null,
                count int not null ,
                amount double not null,
                isConfirmed boolean,
                foreign key (packId) references packaging(packId) on update cascade on delete cascade
);





insert into tea_types values("T001","Black Tea",0);
insert into tea_types values("T002","Green Tea",0);
insert into tea_types values("T003","Oolong Tea",0);


INSERT  INTO  packaging VALUES ("P001","T001","500g",10,150);
INSERT  INTO  packaging VALUES ("P002","T001","1Kg",10,500);
INSERT  INTO  packaging VALUES ("P003","T002","500g",10,200);


SELECT tea_types.type AS Tea_Type, packaging.description AS Description, tea_order_details.qty AS Qty
FROM tea_order_details
         JOIN packaging ON tea_order_details.packId = packaging.packId
         JOIN tea_types ON packaging.typeId = tea_types.typeId
WHERE tea_order_details.tea_order_id = "O001";



CREATE TABLE teacraft_details(
                                 detailsId VARCHAR(30) NOT NULL PRIMARY KEY,
                                 priceOfTeaLeaves DOUBLE,
                                 hourlyRate DOUBLE,
                                 oT DOUBLE
);

INSERT INTO teacraft_details VALUES ("TD001", 200, 1000, 1500);