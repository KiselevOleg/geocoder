/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * место.
 *
 * @param latitude Широта
 * @param longitude Долгота
 * @param displayName имя
 * @param type тип
 */
public record NominatimPlace(
    @JsonProperty("lat") Double latitude,
    @JsonProperty("lon") Double longitude,
    @JsonProperty("display_name") String displayName,
    @JsonProperty("type") String type
) {
    public NominatimPlace() {
        this(0.0, 0.0, "", "");
    }
}
