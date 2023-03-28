/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kubsu.geocoder.model.AddressByCoordinates;

import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@Repository
public interface AddressByCoordinatesRepository extends CrudRepository<AddressByCoordinates, Integer> {
    Optional<AddressByCoordinates> findByInLatitudeAndInLongitude(Double inLatitude, Double inLongitude);
}
