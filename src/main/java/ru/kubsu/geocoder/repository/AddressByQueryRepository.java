/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kubsu.geocoder.model.AddressByQuery;

import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@Repository
public interface AddressByQueryRepository extends CrudRepository<AddressByQuery, Integer> {
    Optional<AddressByQuery> findByQuery(String query);
    Optional<AddressByQuery> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
