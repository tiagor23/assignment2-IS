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
@Table("professors")
public class Professor {
    @Id
    @Column("id")
    private long id;

    @Column("name")
    private String name;

    public void setProfessor(Professor professor){
        id = professor.getId();
        name = professor.getName();
    }
}
