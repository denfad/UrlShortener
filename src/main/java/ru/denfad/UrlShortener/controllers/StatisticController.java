package ru.denfad.UrlShortener.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.denfad.UrlShortener.dto.UrlStatistic;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.service.StatsService;
import ru.denfad.UrlShortener.service.impl.UrlServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/stats")
@Validated
public class StatisticController {

    @Autowired
    private StatsService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UrlStatistic> getStatsByPage(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") int size){
        return service.getStatsPage(page,size);
    }

}
