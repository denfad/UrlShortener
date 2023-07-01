package ru.denfad.UrlShortener.repository;

import org.springframework.data.domain.Page;
import ru.denfad.UrlShortener.model.UrlDocument;

import java.util.List;


public interface UrlRepository {
    UrlDocument readAndUpdateUrl(int id);

}
