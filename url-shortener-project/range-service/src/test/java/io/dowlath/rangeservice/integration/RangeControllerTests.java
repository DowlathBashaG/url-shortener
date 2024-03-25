package io.dowlath.rangeservice.integration;


import io.dowlath.rangeservice.container.MySQLContainerBase;
import io.dowlath.rangeservice.entity.Range;
import io.dowlath.rangeservice.model.response.RangeResponse;
import io.dowlath.rangeservice.repository.RangeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RangeControllerTests extends MySQLContainerBase {

    @Autowired
    WebTestClient webClient;

    @Autowired
    private RangeRepository rangeRepository;

    private static final int REPEATED_TEST_VALUE = 5;

    private static List<RangeResponse> rangeList = new ArrayList<>();

    @BeforeAll
    void setup() {
        rangeRepository.save(new Range());
    }

    @DisplayName("Locking when concurrent requests are made: select for update")
    @Execution(ExecutionMode.CONCURRENT)
    @RepeatedTest(value = REPEATED_TEST_VALUE)
    void whenMultipleRequestsAreMadeThenFindsNewValueByLockingRequest2() {
        rangeList.add(webClient
                .get()
                .uri("/api/v1/range")
                .header("Content-Type", APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RangeResponse.class)
                .returnResult()
                .getResponseBody());
    }

    @DisplayName("There are " + REPEATED_TEST_VALUE  + " requests and no duplicate range value")
    @AfterAll
    void end() {
        assertEquals(REPEATED_TEST_VALUE, rangeList.size());

        assertThat(rangeList).doesNotHaveDuplicates();
    }

}
