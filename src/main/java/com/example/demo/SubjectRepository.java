package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
}