package ru.denfad.UrlShortener.model;

import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document("urls")
@PropertySource("classpath:application.properties")
public class UrlDocument {

    @Transient
    public static final String SERVER_NAME = "server1";

    @Id
    private int id;

    private String url;

    private int redirects = 0;

    @Indexed(name = "createdAtIndex", expireAfter = "#{@environment.getProperty('url.shortener.ttl')}")
    private Date createdAt;



    public UrlDocument(String url, Date createdAt) {
        this.url = url;
        this.createdAt = createdAt;
    }

    public UrlDocument() {

    }

    public void fixTime() {
        this.createdAt = Date.from(Instant.now());
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
