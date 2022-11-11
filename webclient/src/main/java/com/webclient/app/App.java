package com.webclient.app;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.reactive.function.client.WebClient;

import com.webclient.app.model.Student;


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
                FileWriter fileWriter = new FileWriter("namesBirthday.txt", true);
                fileWriter.write("student name " + student.getName() + " birthday: " + student.getBirthDate());
                fileWriter.write("\n");
                fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("countStudents.txt", true);
                        fileWriter.write("Number of students " + count);
                        fileWriter.write("\n");
                        fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("countActiveStudents.txt", true);
                        fileWriter.write("Number of active students " + count);
                        fileWriter.write("\n");
                        fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("allStudentsCoursesMade.txt", true);
                        fileWriter.write("Student's name: " + student.getName() + " // courses made: "
                                + student.getCredits() / 6);
                        fileWriter.write("\n");
                        fileWriter.close();
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

                        FileWriter fileWriter = new FileWriter("averagesStdOfStudents.txt", true);
                        fileWriter.write("Average of all students grades: " + mean
                                + "\nStandard deviation of all students grades: " + std + "\n");
                        fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("allFinalistsStudents.txt", true);
                        fileWriter.write("Student's name: " + student.getName() + " // credits: " + student.getCredits());
                        fileWriter.write("\n");
                        fileWriter.close();
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

                        FileWriter fileWriter = new FileWriter("averagesStdOfGraduates.txt", true);
                        fileWriter.write("Average of graduates grades: " + mean
                                + "\nStandard deviation of graduates grades: " + std + "\n");
                        fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("eldestStudent.txt", true);
                        fileWriter.write("Student's name: " + student.getName() + " // date: " + student.getBirthDate());
                        fileWriter.write("\n");
                        fileWriter.close();
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
                        FileWriter fileWriter = new FileWriter("avgProfPerStud.txt");
                        fileWriter.write("Average professor: " + (counter.get() / students.size() + counter.get() % students.size()) + "\n");
                        fileWriter.close();
                        System.out.println("Average professor done \n");
                    } catch (IOException e) {
                        System.out.println("An IOException was throwned");
                        e.printStackTrace();
                    }
                });
    }
}