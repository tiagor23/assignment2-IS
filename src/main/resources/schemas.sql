CREATE TABLE students (
    id serial PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    birthdate VARCHAR NOT NULL,
    credits integer NOT NULL,
    averageGrade decimal NOT NULL
);

CREATE TABLE professors (
    id serial PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE students_professors ( 
    student_id int REFERENCES students (id) ON DELETE CASCADE ON UPDATE CASCADE,
    professor_id int REFERENCES professors (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT student_professor_pkey PRIMARY KEY (student_id, professor_id)
)