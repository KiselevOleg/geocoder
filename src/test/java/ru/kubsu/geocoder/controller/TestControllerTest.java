package ru.kubsu.geocoder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.dto.RestApiError;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {
  @LocalServerPort
  private Integer port;
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @AfterAll
  static void afterAll() {
    //System.out.println("after all");
  }
  @BeforeAll
  static void beforeAll() {
    //System.out.println("before all");
  }

  @BeforeEach
  void setUp() {
    //System.out.println("before each");
  }
  @AfterEach
  void tearDown() {
    //System.out.println("after each");
  }

  @Test
  void integrationTest() {
    //System.out.println("testm");

        /*ResponseEntity<String> response = testRestTemplate
                .getForEntity("http://localhost:"+this.port+"/tests/test/id1?name=test",
                        String.class);

        //Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //System.out.println(response.getBody());

        String body = response.getBody();
        assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);*/

    //System.out.println("testm");

    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/test/id1?name=test",
        ru.kubsu.geocoder.model.Test.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());


    ru.kubsu.geocoder.model.Test body = response.getBody();
    assertEquals(1,body.getId());
    assertEquals("test",body.getName());
    assertEquals(null,body.getDone());
    assertEquals(null,body.getMark());
  }
  @Test
  void integrationTestWhenNameIsNull() {//NegativeTest
        /*
        ResponseEntity<Map<String, String>> response = testRestTemplate
                .exchange("http://localhost:"+this.port+"/tests/test/id1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Map<String, String>>() {});
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        */
    ResponseEntity<HashMap<String, String>> response = testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/test/id1", HttpMethod.GET, null,
        new ParameterizedTypeReference<HashMap<String, String>>() {});
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    Map<String, String> body = response.getBody();
    System.out.println(body);
    assertEquals("/tests/test/id1", body.get("path")); body.remove("path");
    assertEquals("Bad Request", body.get("error")); body.remove("error");
    assertEquals("400", body.get("status")); body.remove("status");
    body.remove("timestamp");
    assertEquals(true, body.isEmpty());
  }
  @Test
  void integrationTestWhenIdIsString() {//NegativeTest
    ResponseEntity<RestApiError> response = testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/test/idabc?name=test", HttpMethod.GET, null,
        RestApiError.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    RestApiError body = response.getBody();
    System.out.println(body);
    assertEquals("/tests/test/idabc", body.getPath());
    assertEquals("Bad Request", body.getError());
    assertEquals(400, body.getStatus());
  }
}