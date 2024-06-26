package io.dowlath.urlshortenerservice.unit;

import io.dowlath.urlshortenerservice.client.RangeClient;
import io.dowlath.urlshortenerservice.model.Range;
import io.dowlath.urlshortenerservice.service.impl.RangeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RangeServiceTests {

    @Mock
    RangeClient rangeClient;

    @InjectMocks
    RangeServiceImpl rangeService;

    @DisplayName("Fetch range if start and end are null")
    @Test
    void whenOnlyRangeIsNullThenFetch() {
        when(rangeClient.fetchRange()).thenReturn(new Range(1L, 4L));

        rangeService.getUniqueNumber();
        rangeService.getUniqueNumber();
        rangeService.getUniqueNumber();

        verify(rangeClient, times(1)).fetchRange();
    }

    @DisplayName("Fetch new range if out of range")
    @Test
    void whenRangeOutOfRangeThenFetch() {
        when(rangeClient.fetchRange()).thenReturn(new Range(6L, 8L)).thenReturn(new Range(10L, 15L));

        assertEquals(7L, rangeService.getUniqueNumber());
        assertEquals(8L, rangeService.getUniqueNumber());
        assertEquals(11L, rangeService.getUniqueNumber());

        verify(rangeClient, times(2)).fetchRange();
    }
}
