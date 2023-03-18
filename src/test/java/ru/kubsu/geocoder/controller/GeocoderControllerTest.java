package ru.kubsu.geocoder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.dto.NominatimPlace;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.repository.TestRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeocoderControllerTest {
    @MockBean
    private NominatimClient nominatimClient;

    @Autowired
    private TestRepository testRepository;
    @LocalServerPort
    private Integer port;
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @AfterAll
    static void afterAll() {}
    @BeforeAll
    static void beforeAll() {}

    @BeforeEach
    void setUp() {}
    @AfterEach
    void tearDown() {}

    public static NominatimPlace buildTestPlaceSearch () {
        return new NominatimPlace(45.02036085,
            39.03099994504268,
            "Кубанский государственный университет, " +
                "улица Димитрова, Карасунский округ, " +
                "Краснодар, городской округ Краснодар, " +
                "Краснодарский край, " +
                "Южный федеральный округ, 3" +
                "50000, Россия",
            "university");
    }
    public static NominatimPlace buildTestPlaceReverse () {
        return new NominatimPlace(45.02036085,
            39.03099994504268,
            "Кубанский государственный университет, " +
                "улица Димитрова, Карасунский округ, " +
                "Краснодар, городской округ Краснодар, " +
                "Краснодарский край, " +
                "Южный федеральный округ, 3" +
                "50000, Россия",
            null);
    }
    @Test
    void getLocationObjectByNameIntegrationTest() {
        when(nominatimClient.search(anyString())).thenReturn(Optional.of(buildTestPlaceSearch()));

        ResponseEntity<NominatimPlace> response = testRestTemplate
            .getForEntity("http://localhost:"+this.port+"/geocoder/getLocationObjectByName?name=кубгу",
                NominatimPlace.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final NominatimPlace body= response.getBody();
        assertEquals(buildTestPlaceSearch(),body);
        /*assertEquals(45.02036085, body.latitude());
        assertEquals(39.03099994504268, body.longitude());
        assertEquals("university", body.type());
        assertEquals("Кубанский государственный университет, " +
            "улица Димитрова, Карасунский округ, " +
            "Краснодар, городской округ Краснодар, " +
            "Краснодарский край, " +
            "Южный федеральный округ, 3" +
            "50000, Россия", body.displayName());*/
    }
    @Test
    void getLocationObjectByNameIntegrationTestWhenNominatimNotResponse() {
        when(nominatimClient.search(anyString())).thenReturn(Optional.empty());

        ResponseEntity<NominatimPlace> response = testRestTemplate
            .getForEntity("http://localhost:"+this.port+"/geocoder/getLocationObjectByName?name=кубгу",
                NominatimPlace.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        final NominatimPlace body= response.getBody();
        assertNull(body);
    }
    @Test
    void getLocationObjectByNameIntegrationTestWhenNoParameters() {
        when(nominatimClient.search(anyString())).thenReturn(Optional.of(buildTestPlaceSearch()));

        ResponseEntity<RestApiError> response = testRestTemplate
            .getForEntity("http://localhost:"+this.port+"/geocoder/getLocationObjectByName",
                RestApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final RestApiError body= response.getBody();
        assertEquals("/geocoder/getLocationObjectByName", body.path());
        assertEquals("Bad Request", body.error());
        assertEquals(400, body.status());
    }
    @Test
    void getLocationObjectByCoordinatesIntegrationTest() {
        when(nominatimClient.reverse(anyDouble(),anyDouble())).thenReturn(Optional.of(buildTestPlaceReverse()));

        ResponseEntity<NominatimPlace> response = testRestTemplate
            .getForEntity("http://localhost:"+this.port+"/geocoder/getLocationObjectByCoordinates?latitude=45.019634&longitude=39.031161",
                NominatimPlace.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final NominatimPlace body= response.getBody();
        assertEquals(45.02036085, body.latitude());
        assertEquals(39.03099994504268, body.longitude());
        assertEquals(null, body.type());
        assertEquals("Кубанский государственный университет, " +
            "улица Димитрова, Карасунский округ, " +
            "Краснодар, городской округ Краснодар, " +
            "Краснодарский край, " +
            "Южный федеральный округ, 3" +
            "50000, Россия", body.displayName());
    }
    @Test
    void getLocationObjectByCoordinatesIntegrationTestWhenNoParameters() {
        when(nominatimClient.reverse(anyDouble(),anyDouble())).thenReturn(Optional.of(buildTestPlaceReverse()));

        ResponseEntity<RestApiError> response = testRestTemplate
            .getForEntity("http://localhost:"+this.port+"/geocoder/getLocationObjectByCoordinates",
                RestApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final RestApiError body= response.getBody();
        assertEquals("/geocoder/getLocationObjectByCoordinates", body.path());
        assertEquals("Bad Request", body.error());
        assertEquals(400, body.status());
    }
}
