package io.dowlath.urlshortenerservice.service.impl;

import io.dowlath.urlshortenerservice.model.Base62Converter;
import io.dowlath.urlshortenerservice.entity.Url;
import io.dowlath.urlshortenerservice.repository.UrlRepository;
import io.dowlath.urlshortenerservice.service.RangeService;
import io.dowlath.urlshortenerservice.service.UrlService;
import jakarta.el.MethodNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.dowlath.urlshortenerservice.model.response.ShortUrlResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final RangeService rangeService;

    @Override
    @Cacheable(value = "urlCache", key = "#shortenedUrl")
    public Url getLongUrlFromShortUrl(String shortenedUrl) {
        return urlRepository
                .findByShortUrl(shortenedUrl)
                .orElseThrow(() -> new MethodNotFoundException("Long url is not found"));
    }

    @Override
    @Transactional
    public ShortUrlResponse createShortUrlFromLongUrl(String longUrl) {
        String shortUrl = Base62Converter.encodeUniqueNumberToUniqueString(rangeService.getUniqueNumber());

        urlRepository.save(new Url(shortUrl, longUrl));

        return new ShortUrlResponse(shortUrl);
    }
}