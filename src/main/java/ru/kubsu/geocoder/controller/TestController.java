/**
 * Copyright 2023 Kiselev Oleg
 */
package ru.kubsu.geocoder.controller;

import ch.qos.logback.core.status.Status;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.service.TestService;

import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiselev Oleg
 */
@RestController
@RequestMapping("tests")
public class TestController {
    private final TestService service;

    @Autowired
    public TestController(final TestService service) {
        this.service = service;
    }

    @GetMapping(value = "/test/id{id}", produces = APPLICATION_JSON_VALUE)
    public Test getTest(final @PathVariable Integer id, final @RequestParam String name) {
        return service.built(id, name);
    }

    @GetMapping(value = "/addTest", produces = APPLICATION_JSON_VALUE)
    public void save(final @RequestParam String name) {
        service.save(name);
    }

    @GetMapping(value = "/getTest/{name}", produces = APPLICATION_JSON_VALUE)
    public Test load(final @PathVariable String name) {
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

    @GetMapping(value = "/hello", produces = APPLICATION_JSON_VALUE)
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    @SuppressFBWarnings("DMI_RANDOM_USED_ONLY_ONCE")
    public ResponseEntity<?> hello() {
        //react on not found
        //return null; //body is empty
        //throw new EntityNotFoundException(); //error 500
        //return ResponseEntity.ok("test body"); //clearly return type of response
        //return ResponseEntity.notFound().build(); //clearly return type of response
        ////clearly return type of response with body
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("test body");

        if ((new Random()).nextDouble() > 0.5) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"result\": \"data\"}");
        } else {
            final RestApiError error = new RestApiError(Status.ERROR, "not found data description", "/tests/hello");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
