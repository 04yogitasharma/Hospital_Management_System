create database hospital;
show databases;
use hospital;
create table patients(id int auto_increment primary key,name varchar(255) not null,age int not null,gender varchar(10) not null,email varchar(255));
create table doctors(id int auto_increment primary key,name varchar(255) not null,specialization varchar(255) not null);
create table appointment(id int auto_increment primary key, patient_id int not null,doctor_id int not null,appointment_date date not null,foreign key(patient_id)references patients(id),foreign key(doctor_id) references doctors(id));
show tables;
insert into doctors(name,specialization)values("Pankaj Sharma","Physician"); 
insert into doctors(name,specialization)values("Sakshi Sharma","Physiotherapist"); 
select * from doctors;
select * from patients;
desc patients;
alter table appointment add column status varchar(255) default 'pending';
select * from appointment;
alter table appointment drop column status;
alter table appointment add column status enum('pending','completed','cancelled') default 'pending';
CREATE TABLE doctor_availability (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    available_day VARCHAR(20),   -- E.g., Monday, Tuesday, etc.
    available_time TIME,         -- E.g., 09:00:00
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
ALTER TABLE patients ADD COLUMN email VARCHAR(255);
drop database hospital;
drop table appointments;
update patients set email="vikaspandat008@gmail.com" where id=2;