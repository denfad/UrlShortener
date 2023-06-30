package ru.denfad.UrlShortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.denfad.UrlShortener.model.UrlDocument;

@Repository
public interface UrlMongoRepository extends MongoRepository<UrlDocument, Integer> {

}
