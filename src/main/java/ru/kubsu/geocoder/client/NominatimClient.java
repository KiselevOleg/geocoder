/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kubsu.geocoder.dto.NominatimPlace;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiselev Oleg
 */
@FeignClient(name = "NominatimClient", url = "https://nominatim.openstreetmap.org")
public interface NominatimClient {
    String JSON_FORMAT = "json";

    /**
     * поиск обьекта аддресса по названию.
     *
     * @param query название
     * @param format формат
     * @return список обьектов аддресса
     */
    @RequestMapping(method = RequestMethod.GET, value = "/search", produces = "application/json")
    List<NominatimPlace> search(@RequestParam("q") String query, @RequestParam("format") String format);
    /**
     * поиск обьекта на карте по адресной строке в свободном формате.
     * В случае нескольких подходящий обьектов будет возвращен самый релевантный.
     *
     * @param query название
     * @return обьект адреса
     */
    default Optional<NominatimPlace> search(final String query) {
        try {
            return Optional.of(search(query, JSON_FORMAT).get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * получение аддресса по кординатам.
     *
     * @param latitude долгота
     * @param longitude ширина
     * @param format формат
     * @return обьект аддресса
     */
    @RequestMapping(method = RequestMethod.GET, value = "/reverse", produces = "application/json")
    NominatimPlace reverse(@RequestParam("lat") Double latitude,
                           @RequestParam("lon") Double longitude,
                           @RequestParam("format") String format);
    /**
     * поиск обьекта аддресса по координатам.
     *
     * @param latitude долгота
     * @param longitude ширина
     * @return обьект аддресса
     */
    default Optional<NominatimPlace> reverse(final @RequestParam("lat") Double latitude,
                                             final @RequestParam("lon") Double longitude) {
        try {
            return Optional.of(reverse(latitude, longitude, JSON_FORMAT));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
