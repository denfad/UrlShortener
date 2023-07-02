package ru.denfad.UrlShortener.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.denfad.UrlShortener.dto.UrlStatistic;
import ru.denfad.UrlShortener.model.UrlDocument;

import java.util.List;

public interface StatsService {

    List<UrlStatistic> getStatsPage(int page, int size);

}
