/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.model;

import javax.persistence.*;

/**
 * тестовый класс-сущьность для примера синтаксиса.
 *
 * @param id идентификатор
 * @param name имя
 * @param done сделанность
 * @param mark отценка
 */
@Entity
public record Test(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id,
    @Column(name = "name", length = 50, nullable = false, unique = true) String name,
    @Column(name = "done", nullable = false, unique = false) Boolean done,
    @Enumerated(EnumType.STRING)
    @Column(name = "mark", nullable = false, unique = false)
    Mark mark
) {
}
