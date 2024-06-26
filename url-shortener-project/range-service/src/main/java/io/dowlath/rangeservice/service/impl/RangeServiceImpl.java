package io.dowlath.rangeservice.service.impl;

import io.dowlath.rangeservice.entity.Range;
import io.dowlath.rangeservice.model.response.RangeResponse;
import io.dowlath.rangeservice.repository.RangeRepository;
import io.dowlath.rangeservice.service.RangeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static io.dowlath.rangeservice.model.RangeOperations.incrementRange;

@Service
@RequiredArgsConstructor
public class RangeServiceImpl implements RangeService {
    private final RangeRepository rangeRepository;

    @Value("${range.startAdder:1}")
    private Long start;

    @Value("${range.endAdder:1000}")
    private Long end;

    @Transactional
    public RangeResponse getRange() {
        Range range = rangeRepository
                .findTopBy()
                .orElse(new Range());

        incrementRange(range, start, end);

        rangeRepository.save(range);

        return new RangeResponse(range.getStart(), range.getEnd());
    }
}