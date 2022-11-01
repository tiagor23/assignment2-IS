package main.reactor.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

    @GetMapping("/students-professors/{id}")
    Mono<ResponseEntity<StudentProfessor>> getStudentProfessorGivenStudent(@PathVariable Integer id){
        return studentProfessorRepository.findById(id).map(studentProfessor -> {
            return new ResponseEntity<>(studentProfessor, HttpStatus.OK);
        });
    }

    @GetMapping("/students-professors/students/{id}")
    Flux<Integer> getStudentGivenProfessor(@PathVariable Integer id){
        return studentProfessorRepository.findAllStudentsByProfessorId(id);
    }

    @GetMapping("/students-professors/professors/{id}")
    Flux<Integer> getProfessor(@PathVariable Integer id){
        return studentProfessorRepository.findAllByStudentId(id);
    }

    @PostMapping("/students-professors")
    Mono<ResponseEntity<StudentProfessor>> postStudent(@RequestBody StudentProfessor newRelationship){
        return studentProfessorRepository.save(newRelationship).map(relationship -> {
            return new ResponseEntity<>(relationship, HttpStatus.CREATED);
        });
    }

    @DeleteMapping("/students-professors/{id}")
    Mono<Object> deleteStudentProfessor(@PathVariable Integer id){
        return studentProfessorRepository.deleteById(id).map(studentProfessor ->{
            return new ResponseEntity<>(studentProfessor, HttpStatus.OK);
        });
    }

}