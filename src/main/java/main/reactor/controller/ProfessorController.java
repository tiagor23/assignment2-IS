package main.reactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.reactor.model.Professor;
import main.reactor.repository.ProfessorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professors")
    Flux<Professor> getProfessors(){
        return professorRepository.findAll();
    }

    @GetMapping("/professors/{id}")
    Mono<ResponseEntity<Professor>> getProfessor(@PathVariable Integer id){
        return professorRepository.findById(id).map(professor -> {
            return new ResponseEntity<>(professor, HttpStatus.OK);
        });
    }

    @PostMapping("/professors")
    Mono<ResponseEntity<Professor>> postProfessor(@RequestBody Professor newProfessor){
        return professorRepository.save(newProfessor).map(professor -> {
            return new ResponseEntity<>(professor, HttpStatus.CREATED);
        });
    }
}
