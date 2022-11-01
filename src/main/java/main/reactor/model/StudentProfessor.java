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
@Table("students_professors")
public class StudentProfessor {
    @Id
    @Column("id")
    private long id;

    @Column("student_id")
    private Integer student_id;

    @Column("professor_id")
    private Integer professor_id;
}
