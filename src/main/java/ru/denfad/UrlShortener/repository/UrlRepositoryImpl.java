package ru.denfad.UrlShortener.repository;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.denfad.UrlShortener.model.UrlDocument;

@Component
public class UrlRepositoryImpl implements UrlRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UrlRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UrlDocument readAndUpdateUrl(int id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.inc("redirects", 1);
        return mongoTemplate.findAndModify(query, update, UrlDocument.class);
    }
}
