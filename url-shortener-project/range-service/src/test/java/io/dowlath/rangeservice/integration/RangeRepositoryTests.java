package io.dowlath.rangeservice.integration;

import io.dowlath.rangeservice.container.MySQLContainerBase;
import io.dowlath.rangeservice.entity.Range;
import io.dowlath.rangeservice.repository.RangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class RangeRepositoryTests extends MySQLContainerBase {

    @Autowired
    RangeRepository rangeRepository;

    @BeforeEach
    public void setup() {
        rangeRepository.save(new Range());
    }

    @DisplayName("Find range")
    @Test
    void whenGivenCorrectThenFindsRange() {
        Range range = rangeRepository.findTopBy().orElse(null);

        assertNotNull(range);
    }
}
