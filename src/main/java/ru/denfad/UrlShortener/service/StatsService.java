package ru.denfad.UrlShortener.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.denfad.UrlShortener.model.UrlDocument;

public interface StatsService {

    Page<UrlDocument> getStatsPage(int page, int size);
}
