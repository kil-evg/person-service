package ait.cohort46.person.dao;

import ait.cohort46.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person[] findByAddress_City(String city);
    Person[] findByNameIgnoreCase(String name);
    Person[] findByBirthDateBetween(LocalDate from, LocalDate to);
}
