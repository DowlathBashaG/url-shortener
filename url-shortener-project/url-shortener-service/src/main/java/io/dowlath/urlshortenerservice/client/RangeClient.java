package io.dowlath.urlshortenerservice.client;

import io.dowlath.urlshortenerservice.config.RangeClientConfig;
import io.dowlath.urlshortenerservice.model.Range;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "range-service", configuration = RangeClientConfig.class)
public interface RangeClient {

    @GetMapping(value = "api/v1/range", produces = "application/json")
    Range fetchRange();

}