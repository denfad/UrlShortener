package ru.denfad.UrlShortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.service.StatsService;
import ru.denfad.UrlShortener.service.impl.UrlServiceImpl;


@RestController
@RequestMapping("/stats")
public class StatisticController {

    @Autowired
    private StatsService service;

    @GetMapping(path = "/{page}", produces = "application/json")
    public Page<UrlDocument> redirect(@PathVariable int page){
        return service.getStatsPage(page,5);
    }

}
