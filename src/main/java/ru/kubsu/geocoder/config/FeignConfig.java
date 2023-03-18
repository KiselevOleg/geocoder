/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kiselev Oleg
 */
@Configuration
@EnableFeignClients(basePackages = "ru.kubsu.geocoder")
public class FeignConfig {
}
