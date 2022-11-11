package main.reactor.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    Logger logger = Logger.getLogger(ProfessorController.class.getName());

    @GetMapping("/professors")
    Flux<Professor> getProfessors(){
        logger.info("Accessed all professors");
        return professorRepository.findAll();
    }

    @GetMapping("/professors/{id}")
    Mono<ResponseEntity<Professor>> getProfessor(@PathVariable Integer id){
        logger.info("Accessed professor given id; " + id);
        return professorRepository.findById(id).map(professor -> {
            return new ResponseEntity<>(professor, HttpStatus.OK);
        });
    }

    @PostMapping("/professors")
    Mono<ResponseEntity<Professor>> postProfessor(@RequestBody Professor newProfessor){
        logger.info("Created new professor");
        return professorRepository.save(newProfessor).map(professor -> {
            return new ResponseEntity<>(professor, HttpStatus.CREATED);
        });
    }

    @PutMapping("/professors/{id}")
    Mono<ResponseEntity<Professor>> updateProfessor(@PathVariable Integer id, @RequestBody Professor data){
        logger.info("Updated professor given id: " + id);
        return professorRepository.findById(id)
            .switchIfEmpty(Mono.error(new Exception("The given professor doesn't exist")))
            .flatMap(professor -> {
                professor.setProfessor(data);
                return professorRepository.save(professor).map(moddedProfessor -> {
                    return new ResponseEntity<>(professor, HttpStatus.ACCEPTED);
                });
            });
    }

    @DeleteMapping("/professors/{id}")
    Mono<Object> deleteProfessor(@PathVariable Integer id){
        logger.info("Deleted professor given id: " + id);
        return professorRepository.deleteById(id).map(professor -> {
            return new ResponseEntity<>(professor, HttpStatus.OK);
        });
    }

}
