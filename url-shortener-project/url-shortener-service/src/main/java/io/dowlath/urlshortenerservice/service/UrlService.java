package io.dowlath.urlshortenerservice.service;

import io.dowlath.urlshortenerservice.entity.Url;
import io.dowlath.urlshortenerservice.model.response.ShortUrlResponse;

public interface UrlService {

    Url getLongUrlFromShortUrl(String shortenedUrl);

    ShortUrlResponse createShortUrlFromLongUrl(String longUrl);
}
