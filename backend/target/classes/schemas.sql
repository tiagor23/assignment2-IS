CREATE TABLE students (
    id int PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    birthdate VARCHAR NOT NULL,
    credits integer NOT NULL,
    averageGrade decimal NOT NULL
);

CREATE TABLE professors (
    id int PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE students_professors ( 
    id int PRIMARY KEY NOT NULL,
    student_id int REFERENCES students (id) ON DELETE CASCADE ON UPDATE CASCADE,
    professor_id int REFERENCES professors (id) ON DELETE CASCADE ON UPDATE CASCADE,
)