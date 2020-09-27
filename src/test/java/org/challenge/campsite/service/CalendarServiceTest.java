package org.challenge.campsite.service;

import org.challenge.campsite.CampsiteApplication;
import org.challenge.campsite.entity.Reservation;
import org.challenge.campsite.vo.CalendarVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CampsiteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalendarServiceTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    @Test
    public void testCheckAvailability() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/calendar"))
                .queryParam("startDate", LocalDate.now().plusDays(1))
                .queryParam("endDate", LocalDate.now().plusMonths(1));

        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(null, headers);
        ResponseEntity<Set<CalendarVO>> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, entity, new ParameterizedTypeReference<Set<CalendarVO>>() {
                });
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Showing availability: " + response.getBody());
        } else {
            System.out.println("Can't get availability: " + response.getStatusCode() + " " + response.getBody());
        }

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
