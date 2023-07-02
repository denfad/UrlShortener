package ru.denfad.UrlShortener.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denfad.UrlShortener.service.UrlService;
import ru.denfad.UrlShortener.service.impl.UrlServiceImpl;

import java.net.URI;


@RestController
@RequestMapping(path = "/r")
public class RedirectController {

    @Autowired
    private UrlService service;

    @GetMapping("/{url}")
    public ResponseEntity<Void> redirect(@PathVariable String url){
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(service.redirect(url))).build();
    }

}
