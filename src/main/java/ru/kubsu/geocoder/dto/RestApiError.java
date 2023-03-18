/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.dto;

/**
 * аддресс.
 *
 * @param status статис
 * @param error ошибка
 * @param path путь
 */
public record RestApiError(
    Integer status,
    String error,
    String path
) {
    public RestApiError() {
        this(0, "", "");
    }
}
