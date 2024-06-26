package io.dowlath.urlshortenerservice.controller;

import io.dowlath.urlshortenerservice.model.request.LongUrlRequest;
import io.dowlath.urlshortenerservice.model.response.ShortUrlResponse;
import io.dowlath.urlshortenerservice.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/shortUrl")
public class UrlController {

    private final UrlService urlService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponse> createShortUrlFromLongUrl(@Valid @RequestBody LongUrlRequest longUrlRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlService.createShortUrlFromLongUrl(longUrlRequest.longUrl()));
    }

    @GetMapping(value = "/{shortenedUrl}")
    public ResponseEntity<String> redirectToLongUrlFromShortUrl(@PathVariable String shortenedUrl) {
        String longUrl = urlService.getLongUrlFromShortUrl(shortenedUrl).getLongUrl();
       return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}