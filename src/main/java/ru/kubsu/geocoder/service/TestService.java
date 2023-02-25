package ru.kubsu.geocoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.repository.TestRepository;

import javax.swing.text.ChangedCharSetException;

@Service
public class TestService {
    private TestRepository repository;

    @Autowired
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public Test built(Integer id, String name) {
        Test test=new Test();
        test.setId(id);
        test.setName(name);
        return test;
    }

    /*public void save(Integer id, String name) {
        Test test=new Test();
        test.setId(id);
        test.setName(name);
        repository.save(test);
    }*/
    public void save(String name) {
        Test test=new Test();
        test.setName(name);
        repository.save(test);
    }
    /*public Test load(Integer id) {
        //return repository.findById(id).orElse(null);
        return repository.findById(id).orElseThrow(()->new RuntimeException("object is not found"));
    }*/
    public Test load(String name) {
        //return repository.findById(id).orElse(null);
        return repository.findByName(name).orElseThrow(()->new RuntimeException("object is not found"));
    }
}
