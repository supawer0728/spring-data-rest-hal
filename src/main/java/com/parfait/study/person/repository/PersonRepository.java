package com.parfait.study.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.parfait.study.person.Person;

@RepositoryRestResource(path = "/people", collectionResourceRel = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {
}
