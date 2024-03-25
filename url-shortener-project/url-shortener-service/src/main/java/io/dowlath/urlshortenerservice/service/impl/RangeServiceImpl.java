package io.dowlath.urlshortenerservice.service.impl;

import io.dowlath.urlshortenerservice.client.RangeClient;
import io.dowlath.urlshortenerservice.model.Range;
import io.dowlath.urlshortenerservice.service.RangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RangeServiceImpl implements RangeService {

    private final RangeClient rangeClient;

    private static Range range = new Range();

    public synchronized Long getUniqueNumber() {
        if(areRangeValuesNullOrOutOfRange()) range = rangeClient.fetchRange();

        incrementRangeByOne();

        return range.getStart();
    }

    private boolean areRangeValuesNullOrOutOfRange() {
        return  range.getStart() == null ||
                range.getEnd() == null   ||
                range.getStart() < 1     ||
                range.getStart() >= range.getEnd();
    }

    private void incrementRangeByOne() {
        range.setStart(range.getStart() + 1);
    }
}
