package io.dowlath.urlshortenerservice.repository;

import io.dowlath.urlshortenerservice.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, Long> {

      Optional<Url> findByShortUrl(String shortUrl);

}
