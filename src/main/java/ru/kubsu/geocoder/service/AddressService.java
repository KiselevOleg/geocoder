/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.model.Address;
import ru.kubsu.geocoder.repository.AddressRepository;

import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@Service
public class AddressService {
    private final NominatimClient nominatimClient;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(final NominatimClient nominatimClient,
                              final AddressRepository addressRepository) {
        this.nominatimClient = nominatimClient;
        this.addressRepository = addressRepository;
    }

    public Optional<Address> search(final String address) {
        return addressRepository.findByAddress(address)
            .or(() -> nominatimClient.search(address)
                .map(p -> addressRepository.save(Address.of(p)))
        );
    }

    public Optional<Address> reverse(final Double latitude, final Double longitude) {
        return addressRepository.findByLatitudeAndLongitude(latitude, longitude)
            .or(() -> nominatimClient.reverse(latitude, longitude)
                .map(p -> addressRepository.save(Address.of(p)))
            );
    }
}
