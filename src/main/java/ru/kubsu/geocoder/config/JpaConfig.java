/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Kiselev Oleg
 */
//@Configuration
@EnableJpaRepositories(basePackages = "ru.kubsu.geocoder")
public class JpaConfig {
}
