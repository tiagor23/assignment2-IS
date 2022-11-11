package com.webclient.app;

import java.io.FileWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.reactive.function.client.WebClient;

import com.webclient.app.model.Student;
import com.webclient.app.model.Professor;
import com.webclient.app.model.StudentProfessor;

import reactor.core.publisher.Flux;

public class App {
    public static void main(String[] args) {

        WebClient webClient = WebClient.create("http://localhost:8080");
        writeNameBirthdays(webClient);
        writeTotalNumberOfStudents(webClient);
        writeNumberOfActiveStudents(webClient);
        writeNumberCourses(webClient);
        writelastYearGraduation(webClient);
        writeEldestStudent(webClient);
        writeAverageNumberProfessors(webClient);
        writeAverageAndStdOfStudents(webClient);
        writeAverageAndStdOfGraduates(webClient);
        // writeStudsPerProf(webClient);

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private static void writeNameBirthdays(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class).subscribe(student -> {
            try {
                FileWriter myWriter = new FileWriter("namesBirthday.txt", true);
                myWriter.write("student name " + student.getName() + " birthday: " + student.getBirthDate());
                myWriter.write("\n");
                myWriter.close();
                System.out.println("The name and birthday file was written");
            } catch (IOException e) {
                System.out.println("An IOException was throwned");
                e.printStackTrace();
            }
        });
    }

    private static void writeTotalNumberOfStudents(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class).count().subscribe(
                count -> {
                    try {
                        FileWriter myWriter = new FileWriter("countStudents.txt", true);
                        myWriter.write("Number of students " + count);
                        myWriter.write("\n");
                        myWriter.close();
                        System.out.println("Count added to file");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }

    private static void writeNumberOfActiveStudents(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .filter(student -> student.getCredits() < 180).count()
                .subscribe(count -> {
                    try {
                        FileWriter myWriter = new FileWriter("countActiveStudents.txt", true);
                        myWriter.write("Number of active students " + count);
                        myWriter.write("\n");
                        myWriter.close();
                        System.out.println("Active count added to file");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }

                });
    }

    private static void writeNumberCourses(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .subscribe(student -> {
                    try {
                        FileWriter myWriter = new FileWriter("allStudentsCoursesMade.txt", true);
                        myWriter.write("Student's name: " + student.getName() + " // courses made: "
                                + student.getCredits() / 6);
                        myWriter.write("\n");
                        myWriter.close();
                        System.out.println("All students' courses done");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }

                });
    }

    private static void writeAverageAndStdOfStudents(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class).collectList()
                .subscribe(students -> {
                    try {
                        float media = 0;
                        for (int i = 0; i < students.size(); i++) {
                            Student stu = students.get(i);
                            media += stu.getAverageGrade();
                        }
                        float mean = media / students.size();
                        double std = 0;
                        for (int i = 0; i < students.size(); i++) {
                            Student stu = students.get(i);
                            std += Math.pow(mean - stu.getAverageGrade(), 2);
                        }
                        std = Math.sqrt(std / students.size());

                        FileWriter myWriter = new FileWriter("averagesStdOfStudents.txt", true);
                        myWriter.write("Average of all students grades: " + mean
                                + "\nStandard deviation of all students grades: " + std + "\n");
                        myWriter.close();
                        System.out.println("Mean and Std done");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }

    private static void writelastYearGraduation(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .filter(student -> student.getCredits() < 180 && student.getCredits() >= 120)
                .sort((student1, student2) -> student1.getCredits().compareTo(student2.getCredits()))
                .subscribe(student -> {
                    try {
                        FileWriter myWriter = new FileWriter("allFinalistsStudents.txt", true);
                        myWriter.write("Student's name: " + student.getName() + " // credits: " + student.getCredits());
                        myWriter.write("\n");
                        myWriter.close();
                        System.out.println("All finalists done");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });

    }

    private static void writeAverageAndStdOfGraduates(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .filter(student -> student.getCredits() > 180).collectList()
                .subscribe(students -> {
                    try {
                        float media = 0;
                        for (int i = 0; i < students.size(); i++) {
                            Student stu = students.get(i);
                            media += stu.getAverageGrade();
                        }
                        float mean = media / students.size();
                        double std = 0;
                        for (int i = 0; i < students.size(); i++) {
                            Student stu = students.get(i);
                            std += Math.pow(mean - stu.getAverageGrade(), 2);
                        }
                        std = Math.sqrt(std / students.size());

                        FileWriter myWriter = new FileWriter("averagesStdOfGraduates.txt", true);
                        myWriter.write("Average of graduates grades: " + mean
                                + "\nStandard deviation of graduates grades: " + std + "\n");
                        myWriter.close();
                        System.out.println("Mean and Std of graduates done");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }

    private static void writeEldestStudent(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .sort((student1, student2) -> {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(student1.getBirthDate())
                                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(student2.getBirthDate()));
                    } catch (ParseException e1) {
                        System.out.println("An Parse exception was throwned");
                        e1.printStackTrace();
                    }
                    return 0;
                }).elementAt(0)
                .subscribe(student -> {
                    try {
                        FileWriter myWriter = new FileWriter("eldestStudent.txt", true);
                        myWriter.write("Student's name: " + student.getName() + " // date: " + student.getBirthDate());
                        myWriter.write("\n");
                        myWriter.close();
                        System.out.println("Eldest Student done");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }

    private static void writeAverageNumberProfessors(WebClient webClient) {
        webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
                .collectList()
                .subscribe(students -> {
                    try {
                        AtomicInteger counter = new AtomicInteger(0);
                        for(Student student : students){
                            webClient.get().uri("/students-professors/professors/" + student.getId()).retrieve()
                                .bodyToFlux(Integer.class).collectList().filter(list -> !list.isEmpty())
                                    .subscribe(list -> {
                                        counter.addAndGet(list.size());
                                    });
                        }

                        System.out.println("\n \n \n \n \n \n \n \n");
                        System.out.println(students);
                        FileWriter myWriter = new FileWriter("avgProfPerStud.txt");
                        myWriter.write("Average professor: " + (counter.get() / students.size() + counter.get() % students.size()) + "\n");
                        myWriter.close();
                        System.out.println("Average professor done \n");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }

    private static void writeStudsPerProf(WebClient webClient) {
        webClient.get().uri("/students-professors").retrieve().bodyToFlux(StudentProfessor.class)
                .collectList();

    }
}