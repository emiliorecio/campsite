package org.challenge;

import org.challenge.campsite.CampsiteApplication;
import org.challenge.campsite.entity.Reservation;
import org.challenge.util.RandomCampsiteUtils;
import org.challenge.util.TaskType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CampsiteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = RandomCampsiteUtils.class)
public class CampsiteApplicationTests {


    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


    private static final int TOTAL_THREADS = 100;

    @LocalServerPort
    private int port = 8080;

    @Autowired
    RandomCampsiteUtils randomCampsiteUtils;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private BlockingQueue<Long> queue = new LinkedBlockingDeque<>();
    private static final String LINE = "------------------------------------------------------------------------------------------------";

    //@Test
    public void testCreateSReservation() throws Exception {
        Reservation reservation = randomCampsiteUtils.getRandomReservation();
        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(reservation, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/reservation"), HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Long idJSON = getIdFromJSON(response, TaskType.CREATE);
            queue.put(idJSON);
            System.out.println(getOkMessageResponse("Reservation created ID: " + idJSON));
        } else {
            System.out.println(getBadMessageResponse("Can't create reservation ID: " + reservation.getId() + " " + response.getStatusCode() + " " + response.getBody() + " " + reservation.toString()));
        }
    }

    //@Test
    public void testModifyReservation() throws Exception {
        Long id = getID();
        Reservation reservation = randomCampsiteUtils.getRandomReservation();
        if (Objects.nonNull(id)) {
            reservation.setId(id);
        }
        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(reservation, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/reservation"), HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Long idJSON = getIdFromJSON(response, TaskType.MODIFY);
            queue.put(id);
            System.out.println(getOkMessageResponse("Reservation modified ID: " + idJSON));
        } else {
            System.out.println(getBadMessageResponse("Can't modify reservation ID: " + reservation.getId() + " " + response.getStatusCode() + " " + response.getBody() + " " + reservation.toString()));
        }
    }

    //@Test
    public void testGetReservation() throws Exception {
        Long id = getID();
        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/reservation/" + id), HttpMethod.GET, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Long idJSON = getIdFromJSON(response, TaskType.GET);
            queue.put(id);
            System.out.println(getOkMessageResponse("Reservation acquired ID: " + idJSON));
        } else {
            System.out.println(getBadMessageResponse("Can't acquire reservation ID: " + id + " " + response.getStatusCode() + " " + response.getBody()));
        }

    }

    //@Test
    public void testDeleteReservation() throws Exception {
        Long id = getID();
        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/reservation/" + id), HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(getOkMessageResponse("Reservation deleted ID: " + id));
        } else {
            System.out.println(getBadMessageResponse("Can't delete reservation ID: " + id + " " + response.getStatusCode() + " " + response.getBody()));
        }

    }

    @Test
    public void runThreads() throws Exception {
        getAllReservationFromDBOrCreateSome();
        ExecutorService service = Executors.newFixedThreadPool(TOTAL_THREADS);
        CountDownLatch latch = new CountDownLatch(TOTAL_THREADS);
        for (int i = 0; i < TOTAL_THREADS; i++) {
            service.submit(() -> {
                try {
                    runTask();
                } catch (Exception e) {
                    System.out.println("Exception caught while testing concurrency: " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();
        //System.out.println("PORT: " + port);
        //Sleep app to check the DB on mem
        //Thread.sleep(30000);
    }

    //@Test
    public void getAllReservationFromDBOrCreateSome() throws Exception {
        HttpEntity<Reservation> entity = new HttpEntity<Reservation>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/reservation/all"), HttpMethod.GET, entity, String.class);
        JSONArray arrayObject = new JSONArray(response.getBody());;
        if(arrayObject.length() > 0){
            for (int i = 0; i < arrayObject.length(); ++i) {
                JSONObject rec = arrayObject.getJSONObject(i);
                Long id = rec.getLong("id");
                queue.put(id);
            }
        }else{
            for(int i = 0; i <= 10; i++){
                testCreateSReservation();
            }
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(getOkMessageResponse("Total reservation acquired: " + queue.size()));
        } else {
            System.out.println(getBadMessageResponse("Can't acquire reservations"));
        }

    }

    private void runTask() throws Exception {
        TaskType task = randomCampsiteUtils.getRandomTask();
        switch (task) {
            case GET:
                testGetReservation();
                break;
            case CREATE:
                testCreateSReservation();
                break;
            case MODIFY:
                testModifyReservation();
                break;
            case DELETE:
                testDeleteReservation();
                break;
        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private String getOkMessageResponse(String msj) {
        return ANSI_GREEN + LINE + "\n" + msj + "\n" + LINE + ANSI_RESET;
    }

    private String getBadMessageResponse(String msj) {
        return ANSI_RED + LINE + "\n" + msj + "\n" + LINE + ANSI_RESET;
    }

    private Long getID() throws InterruptedException {
        if (queue.isEmpty()) {
            return Long.valueOf(randomCampsiteUtils.getRandomTotalGroup());
        } else {
            return queue.poll(1, TimeUnit.SECONDS);
        }
    }

    private Long getIdFromJSON(ResponseEntity<String> response, TaskType taskType) {
        JSONObject myObject;
        try {
            myObject = new JSONObject(response.getBody());
            return Long.valueOf((Integer) myObject.get("id"));
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Tasktype: " + taskType.toString() + " :::: " + response.getBody());
        }
        return 0L;
    }

}

