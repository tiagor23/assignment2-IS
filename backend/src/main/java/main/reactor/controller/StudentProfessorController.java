package main.reactor.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import main.reactor.model.StudentProfessor;
import main.reactor.repository.StudentProfessorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfessorController {
    @Autowired
    private StudentProfessorRepository studentProfessorRepository;
    Logger logger = Logger.getLogger(StudentProfessorController.class.getName());
    
    @GetMapping("/students-professors/{id}")
    Mono<ResponseEntity<StudentProfessor>> getStudentProfessorGivenStudent(@PathVariable Integer id){
        logger.info("Accessed student professor relationship given student id: " + id);
        return studentProfessorRepository.findById(id).map(studentProfessor -> {
            return new ResponseEntity<>(studentProfessor, HttpStatus.OK);
        });
    }

    @GetMapping("/students-professors/students/{id}")
    Flux<Integer> getStudentGivenProfessor(@PathVariable Integer id){
        logger.info("Accessed all students ids in the relationship given professor id: " + id);
        return studentProfessorRepository.findAllStudentsByProfessorId(id);
    }

    @GetMapping("/students-professors/professors/{id}")
    Flux<Integer> getProfessorGivenStudent(@PathVariable Integer id){
        logger.info("Accessed all professors ids in the relationship given student id: " + id);
        return studentProfessorRepository.findAllProfessorsByStudentId(id);
    }

    @PostMapping("/students-professors")
    Mono<ResponseEntity<StudentProfessor>> postStudentProfessor(@RequestBody StudentProfessor newRelationship){
        logger.info("Created new relationship between students and professors");
        return studentProfessorRepository.save(newRelationship).map(relationship -> {
            return new ResponseEntity<>(relationship, HttpStatus.CREATED);
        });
    }

    @DeleteMapping("/students-professors/{id}")
    Mono<Object> deleteStudentProfessor(@PathVariable Integer id){
        logger.info("Deleted relationship between student and professor");
        return studentProfessorRepository.deleteById(id).map(studentProfessor -> {
            return new ResponseEntity<>(studentProfessor, HttpStatus.OK);
        });
    }

}