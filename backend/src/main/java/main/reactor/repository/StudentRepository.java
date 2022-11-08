package main.reactor.repository;

import org.springframework.stereotype.Repository;

import main.reactor.model.Student;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Integer>{}
