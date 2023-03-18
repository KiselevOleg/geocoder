/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kubsu.geocoder.model.Test;

import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@Repository
//                                                     Entity, type of primary key
public interface TestRepository extends CrudRepository<Test, Integer> {
    Optional<Test> findByName(String name);
}
