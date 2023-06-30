package ru.denfad.UrlShortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.denfad.UrlShortener.service.UrlService;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    @Autowired
    private UrlService service;

    @GetMapping
    String generateUrl(@RequestParam("url") String url) {
       return service.saveUrl(url);

    }


}
