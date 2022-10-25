package main.reactor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import main.reactor.model.Professor;

@Repository
public interface ProfessorRepository extends ReactiveCrudRepository<Professor, Integer>{}
