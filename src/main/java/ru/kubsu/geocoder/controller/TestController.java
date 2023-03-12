package ru.kubsu.geocoder.controller;

import ch.qos.logback.core.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.dto.NominatimPlace;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.service.TestService;

import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("tests")
public class TestController {
    private final TestService service;
    private final NominatimClient nominatimClient;

    @Autowired
    public TestController(TestService service, NominatimClient nominatimClient) {
        this.service = service;
        this.nominatimClient = nominatimClient;
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


    @GetMapping(value = "/getLocationObjectByName", produces = APPLICATION_JSON_VALUE)
    public NominatimPlace getLocationObjectByName(@RequestParam String name) {
        return nominatimClient.search(name,"json").get(0);
    }
    @GetMapping(value = "/getLocationObjectByCoordinates", produces = APPLICATION_JSON_VALUE)
    public NominatimPlace getLocationObjectByCoordinates(@RequestParam Double latitude, @RequestParam Double longitude) {
        return nominatimClient.reverse(latitude, longitude, "json");
    }

    @GetMapping(value = "/hello", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello() {
        //react on not found
        //return null; //body is empty
        //throw new EntityNotFoundException(); //error 500
        //return ResponseEntity.ok("test body"); //clearly return type of response
        //return ResponseEntity.notFound().build(); //clearly return type of response
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("test body"); //clearly return type of response with body

        if((new Random()).nextDouble()>0.5) {
            return ResponseEntity.status(HttpStatus.OK).body("result");
        } else {
            RestApiError error = new RestApiError();
            error.setError("not found data description");
            error.setStatus(Status.ERROR);
            error.setPath("/tests/hello");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
