package com.example.hotel.webenvironment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerWebTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllRoomsTest() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/rooms/all", String.class))
                .contains("All Rooms");
    }

    @Test
    public void searchAvailableRooms() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/searchAvailableRooms", String.class))
                .contains("Search for:");
    }

    @Test
    public void getAllCustomers() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/customers/all", String.class))
                .contains("All Customers");
    }
}
