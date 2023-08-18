CREATE DATABASE demo;

CREATE TABLE students (
    studentID SERIAL primary key,
    lastName varchar(255),
    firstName varchar(255),
    age int,
    phoneNumber varchar(10),
    instrument varchar(255)
);

CREATE TABLE lessons (
    lessonID SERIAL primary key,
    dateAndTime timestamp,
    duration int
);

CREATE TABLE schedule (
    scheduleID SERIAL PRIMARY KEY,
    studentID int REFERENCES students(studentID),
    lessonID int REFERENCES lessons(lessonID),
    CONSTRAINT fk_student FOREIGN KEY (studentID) REFERENCES students(studentID) ON DELETE CASCADE,
    CONSTRAINT fk_lesson FOREIGN KEY (lessonID) REFERENCES lessons(lessonID) ON DELETE CASCADE
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    paymentDate timestamp,
    studentName varchar(255),
    paymentAmount float,
    CONSTRAINT fk_payments_students FOREIGN KEY (studentID) REFERENCES students(studentID) ON DELETE CASCADE
);
