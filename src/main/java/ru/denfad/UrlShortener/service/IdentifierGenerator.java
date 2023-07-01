package ru.denfad.UrlShortener.service;

import org.springframework.data.mongodb.core.query.Update;
import ru.denfad.UrlShortener.model.DataBaseSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public interface IdentifierGenerator{
    int generateIdentifier(String serverName);
}
