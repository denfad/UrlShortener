package ru.denfad.UrlShortener.repository;

import ru.denfad.UrlShortener.model.UrlDocument;

public interface UrlRepository {
    UrlDocument readAndUpdateUrl(int id);
}
