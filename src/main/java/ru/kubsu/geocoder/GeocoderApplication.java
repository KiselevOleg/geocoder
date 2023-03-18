/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kiselev Oleg
 */
@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.HideUtilityClassConstructor"})
public class GeocoderApplication {
    public static void main(final String[] args) {
        SpringApplication.run(GeocoderApplication.class, args);
    }
}
