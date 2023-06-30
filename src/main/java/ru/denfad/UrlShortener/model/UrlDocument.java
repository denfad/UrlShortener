package ru.denfad.UrlShortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("urls")
public class UrlDocument {

    @Transient
    public static final String SEQUENCE_NAME = "urls_sequence";

    @Id
    private int id;

    private String url;

    private int redirects = 0;

    public UrlDocument(String url) {
        this.url = url;
    }

    public UrlDocument(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public UrlDocument() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRedirects() {
        return redirects;
    }

    public void setRedirects(int redirects) {
        this.redirects = redirects;
    }
}
