package ru.denfad.UrlShortener.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.denfad.UrlShortener.dto.GenerateDTO;
import ru.denfad.UrlShortener.service.UrlService;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    @Autowired
    private UrlService service;

    @Value("${url.shortener.host}")
    private String host;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GenerateDTO generateUrl(@Valid @RequestBody GenerateDTO request) {
       return new GenerateDTO(host,service.saveUrl(request.getUrl()));

    }


}
