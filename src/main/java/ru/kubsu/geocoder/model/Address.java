/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.model;

import ru.kubsu.geocoder.dto.NominatimPlace;
import javax.persistence.*;

/**
 * аддресс.
 *
 * @param id идентификатор
 * @param address аддресс
 * @param latitude шинина
 * @param longitude долгота
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
@Entity
public record Address(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id,
    @Column(name = "address", nullable = false, unique = true) String address,
    @Column(name = "latitude", nullable = false, unique = false) Double latitude,
    @Column(name = "longitude", nullable = false, unique = false) Double longitude
) {
    public Address() {
        this(null, null, null, null);
    }
    public static Address of(final NominatimPlace place) {
        return new Address(null, place.displayName(), place.latitude(), place.longitude());
    }
}
