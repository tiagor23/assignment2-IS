package main.reactor.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import main.reactor.model.StudentProfessor;
import reactor.core.publisher.Flux;

public interface StudentProfessorRepository extends ReactiveCrudRepository<StudentProfessor, Integer>{
    @Query("SELECT student_id FROM students_professors WHERE professor_id = :professorId")
    Flux<Integer> findAllStudentsByProfessorId(Integer professorId);

    @Query("SELECT professor_id FROM students_professors WHERE student_id = :studentId")
    Flux<Integer> findAllByStudentId(Integer studentId);
    
}
