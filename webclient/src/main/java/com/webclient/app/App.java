package com.webclient.app;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.reactive.function.client.WebClient;

import com.webclient.app.model.Student;

public class App {
    public static void main( String[] args ) {

       WebClient webClient = WebClient.create("http://localhost:8080"); 
       writeNameBirthdays(webClient);
       writeTotalNumberOfStudents(webClient);
       writeNumberOfActiveStudents(webClient);
       writeNumberCourses(webClient);
       writelastYearGraduation(webClient);
       writeEldestStudent(webClient);

        try {
            Thread.sleep(2000);
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
            }
        );
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
                    myWriter.write("Student's name: " +  student.getName() + " // courses made: " +  student.getCredits() / 6);
                    myWriter.write("\n");
                    myWriter.close();
                    System.out.println("All students' courses done");
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
                    myWriter.write("Student's name: " +  student.getName() + " // credits: " +  student.getCredits());
                    myWriter.write("\n");
                    myWriter.close();
                    System.out.println("All finalists done");
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
                    return new SimpleDateFormat("yyyy-MM-dd").parse(student1.getBirthDate()).compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(student2.getBirthDate()));
                } catch (ParseException e1) {
                    System.out.println("An Parse exception was throwned");
                    e1.printStackTrace();
                }
                return 0;
            })
            .subscribe(student -> {
                 try {
                    FileWriter myWriter = new FileWriter("eldestStudent.txt", true);
                    myWriter.write("Student's name: " +  student.getName() + " // date: " + student.getBirthDate());
                    myWriter.write("\n");
                    myWriter.close();
                    System.out.println("Eldest Student done");
                } catch (IOException e) {
                    System.out.println("An IOException was throwned");
                    e.printStackTrace();
                }               
            });

    }
}