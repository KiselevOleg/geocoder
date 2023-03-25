/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kubsu.geocoder.model.Address;

import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Optional<Address> findByAddress(String address);
    Optional<Address> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
