package ru.denfad.UrlShortener.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.denfad.UrlShortener.model.DataBaseSequence;
import ru.denfad.UrlShortener.service.IdentifierGenerator;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGenerator implements IdentifierGenerator {

    @Autowired
    private MongoOperations mongoOperations;

    public int generateIdentifier(String name) {
        DataBaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(name)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DataBaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
