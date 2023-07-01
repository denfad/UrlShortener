package ru.denfad.UrlShortener.service;


public interface UrlService {
    String saveUrl(String url);

    String redirect(String shortUrl);
}
