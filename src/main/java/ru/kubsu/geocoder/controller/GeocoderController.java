/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.dto.NominatimPlace;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiselev Oleg
 */
@RestController
@RequestMapping("geocoder")
public class GeocoderController {
    private final NominatimClient nominatimClient;

    @Autowired
    public GeocoderController(final NominatimClient nominatimClient) {
        this.nominatimClient = nominatimClient;
    }

    /**
     * получение адреса по имени.
     *
     * @param name имя
     * @return обьект аддресса
     */
    @GetMapping(value = "/getLocationObjectByName", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NominatimPlace> getLocationObjectByName(final @RequestParam String name) {
        return nominatimClient.search(name)
            .map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * получение аддресса по координакам.
     *
     * @param latitude ширина
     * @param longitude долгота
     * @return обьект аддресса
     */
    @GetMapping(value = "/getLocationObjectByCoordinates", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NominatimPlace> getLocationObjectByCoordinates(
        final @RequestParam Double latitude, final @RequestParam Double longitude) {
        return nominatimClient.reverse(latitude, longitude)
            .map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
