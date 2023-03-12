package ru.kubsu.geocoder.controller;

import ch.qos.logback.core.status.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.dto.NominatimPlace;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.repository.TestRepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {
  @Autowired
  private TestRepository testRepository;
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
  void getTestIntegrationTest() {
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
  void getTestIntegrationTestWhenNameIsNull() {//NegativeTest
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
  void getTestIntegrationTestWhenIdIsString() {//NegativeTest
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

  @Test
  void saveIntegrationTest() {
    ResponseEntity<String> response = testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/addTest?name=test", HttpMethod.GET, null,
        String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    Iterator<ru.kubsu.geocoder.model.Test> tests = testRepository.findAll().iterator();
    ru.kubsu.geocoder.model.Test test = new ru.kubsu.geocoder.model.Test(); int count=0;
    while(tests.hasNext()) {
      test=tests.next();
      ++count;
    }

    assertEquals(1,count);
    assertEquals("test",test.getName());
    assertEquals(null,test.getDone());
    assertEquals(null,test.getMark());

    testRepository.deleteAll();
  }
  @Test
  void saveIntegrationTestAddExistName() {
    testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/addTest?name=test", HttpMethod.GET, null,
        String.class);
    ResponseEntity<RestApiError> response = testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/addTest?name=test", HttpMethod.GET, null,
        RestApiError.class);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    RestApiError body= response.getBody();
    assertEquals("/tests/addTest", body.getPath());
    assertEquals("Internal Server Error", body.getError());
    assertEquals(500, body.getStatus());

    testRepository.deleteAll();
  }

  @Test
  void loadIntegrationTest() {
    testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/addTest?name=test", HttpMethod.GET, null,
        String.class);
    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getTest/test",
        ru.kubsu.geocoder.model.Test.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    ru.kubsu.geocoder.model.Test body = response.getBody();
    assertEquals("test", body.getName());
    assertEquals(null, body.getDone());
    assertEquals(null, body.getMark());

    testRepository.deleteAll();
  }
  @Test
  void loadIntegrationTestForNotExistName() {
    ResponseEntity<RestApiError> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getTest/test",
        RestApiError.class);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    RestApiError body= response.getBody();
    assertEquals("/tests/getTest/test", body.getPath());
    assertEquals("Internal Server Error", body.getError());
    assertEquals(500, body.getStatus());
  }

  @Test
  void nomanatimServiceIntegrationTest() {
    ResponseEntity<NominatimPlace> response = testRestTemplate
      .getForEntity("https://nominatim.openstreetmap.org/reverse?lat=45.019634&lon=39.031161&format=json",
        NominatimPlace.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    NominatimPlace body= response.getBody();
    assertEquals(45.02036085, body.getLatitude());
    assertEquals(39.03099994504268, body.getLongitude());
    assertEquals(null, body.getType());
    assertEquals("Кубанский государственный университет, " +
      "улица Димитрова, Карасунский округ, " +
      "Краснодар, городской округ Краснодар, " +
      "Краснодарский край, " +
      "Южный федеральный округ, 3" +
      "50000, Россия", body.getDisplayName());
  }
  @Test
  void getLocationObjectByNameIntegrationTest() {
    ResponseEntity<NominatimPlace> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getLocationObjectByName?name=кубгу",
        NominatimPlace.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    NominatimPlace body= response.getBody();
    assertEquals(45.02036085, body.getLatitude());
    assertEquals(39.03099994504268, body.getLongitude());
    assertEquals("university", body.getType());
    assertEquals("Кубанский государственный университет, " +
      "улица Димитрова, Карасунский округ, " +
      "Краснодар, городской округ Краснодар, " +
      "Краснодарский край, " +
      "Южный федеральный округ, 3" +
      "50000, Россия", body.getDisplayName());
  }
  @Test
  void getLocationObjectByNameIntegrationTestWhenNoParameters() {
    ResponseEntity<RestApiError> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getLocationObjectByName",
        RestApiError.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    RestApiError body= response.getBody();
    assertEquals("/tests/getLocationObjectByName", body.getPath());
    assertEquals("Bad Request", body.getError());
    assertEquals(400, body.getStatus());
  }
  @Test
  void getLocationObjectByCoordinatesIntegrationTest() {
    ResponseEntity<NominatimPlace> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getLocationObjectByCoordinates?latitude=45.019634&longitude=39.031161",
        NominatimPlace.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    NominatimPlace body= response.getBody();
    assertEquals(45.02036085, body.getLatitude());
    assertEquals(39.03099994504268, body.getLongitude());
    assertEquals(null, body.getType());
    assertEquals("Кубанский государственный университет, " +
      "улица Димитрова, Карасунский округ, " +
      "Краснодар, городской округ Краснодар, " +
      "Краснодарский край, " +
      "Южный федеральный округ, 3" +
      "50000, Россия", body.getDisplayName());
  }
  @Test
  void getLocationObjectByCoordinatesIntegrationTestWhenNoParameters() {
    ResponseEntity<RestApiError> response = testRestTemplate
      .getForEntity("http://localhost:"+this.port+"/tests/getLocationObjectByCoordinates",
        RestApiError.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    RestApiError body= response.getBody();
    assertEquals("/tests/getLocationObjectByCoordinates", body.getPath());
    assertEquals("Bad Request", body.getError());
    assertEquals(400, body.getStatus());
  }

  @Test
  void helloIntegrationTest() {
    ResponseEntity<Object> response = testRestTemplate
      .exchange("http://localhost:"+this.port+"/tests/getLocationObjectByCoordinates",
        HttpMethod.GET, null,
          new ParameterizedTypeReference<Object>() {});
    Object body=response.getBody();
    assertNotEquals(null, body);
    if(response.getStatusCode()==HttpStatus.OK) {
            assertEquals(String.class, body.getClass());
            assertEquals("result", (String) body);
    } else if(response.getStatusCode()==HttpStatus.BAD_REQUEST) {
        //assertEquals(RestApiError.class, body.getClass());
        //assertEquals("not found data description", ((RestApiError) body).getError());
        //assertEquals(Status.ERROR, ((RestApiError) body).getStatus());
        //assertEquals("/tests/hello", ((RestApiError) body).getPath());
    } else {
        throw new RuntimeException("expected StatusCode OK or NOT_FOUND instead of "+
            response.getStatusCode());
    }
  }
}
