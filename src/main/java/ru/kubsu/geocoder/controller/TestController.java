package ru.kubsu.geocoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.service.TestService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("tests")
public class TestController {
    private final TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping(value = "/test/id{id}", produces = APPLICATION_JSON_VALUE)
    public Test getTest(@PathVariable Integer id, @RequestParam String name) {
        return service.built(id, name);
    }

    @GetMapping(value = "/addTest", produces = APPLICATION_JSON_VALUE)
    public void save(@RequestParam String name) {
        service.save(name);
    }

    @GetMapping(value = "/getTest/{name}", produces = APPLICATION_JSON_VALUE)
    public Test load(@PathVariable String name) {
        return service.load(name);
    }

    /*
    * private final TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping(value = "/test/id{id}", produces = APPLICATION_JSON_VALUE)
    public Test getTest(@PathVariable Integer id, @RequestParam String name) {
        return service.built(id, name);
    }

    @GetMapping(value = "/addTest/id{id}", produces = APPLICATION_JSON_VALUE)
    public void save(@PathVariable Integer id, @RequestParam String name) {
        service.save(id, name);
    }

    @GetMapping(value = "/getTest/id{id}", produces = APPLICATION_JSON_VALUE)
    public Test load(@PathVariable Integer id) {
        return service.load(id);
    }
    * */
}
