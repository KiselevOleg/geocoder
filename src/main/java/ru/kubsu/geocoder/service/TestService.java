/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.repository.TestRepository;

/**
 * @author Kiselev Oleg
 */
@Service
public class TestService {
    private final TestRepository repository;

    @Autowired
    public TestService(final TestRepository repository) {
        this.repository = repository;
    }

    public Test built(final Integer id, final String name) {
        return new Test(id, name, null, null);
    }

    /*public void save(Integer id, String name) {
        Test test=new Test();
        test.setId(id);
        test.setName(name);
        repository.save(test);
    }*/
    public void save(final String name) {
        final Test test = new Test(null, name, null, null);
        repository.save(test);
    }
    /*public Test load(Integer id) {
        //return repository.findById(id).orElse(null);
        return repository.findById(id).orElseThrow(()->new RuntimeException("object is not found"));
    }*/
    public Test load(final String name) {
        //return repository.findById(id).orElse(null);
        return repository.findByName(name).orElseThrow(() -> new RuntimeException("object is not found"));
    }
}
