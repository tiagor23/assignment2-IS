package main.reactor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfessor {
    @Id
    @Column("student_professor_pkey")
    private long id;

    @Column("student_id")
    private Integer student_id;

    @Column("professor_id")
    private Integer professor_id;
}
