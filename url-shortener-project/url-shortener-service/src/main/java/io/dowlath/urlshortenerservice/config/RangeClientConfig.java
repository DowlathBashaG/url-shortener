package io.dowlath.urlshortenerservice.config;

import io.dowlath.urlshortenerservice.decoder.RangeServiceDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class RangeClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RangeServiceDecoder();
    }
}
