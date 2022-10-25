package main.reactor.controller;

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

import main.reactor.model.Student;
import main.reactor.repository.StudentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    Flux<Student> getStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    Mono<ResponseEntity<Student>> getStudent(@PathVariable Integer id){
        return studentRepository.findById(id).map(student -> {
            return new ResponseEntity<>(student, HttpStatus.OK);
        });
    }

    @PostMapping("/students")
    Mono<ResponseEntity<Student>> postStudent(@RequestBody Student newStudent){
        return studentRepository.save(newStudent).map(student -> {
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        });
    }
}
