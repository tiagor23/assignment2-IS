package main.reactor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("students")
public class Student {
    @Id
    @Column("id")
    private long id;

    @Column("name")
    private String name;

    @Column("birthdate")
    private String birthDate;

    @Column("credits")
    private Integer credits;
    
    @Column("averageGrade")
    private float averageGrade; 

    public void setStudent(Student student){
        id = student.getId();
        name = student.getName();
        birthDate = student.getBirthDate();
        credits = student.getCredits();
        averageGrade = student.getAverageGrade();
    }
}
