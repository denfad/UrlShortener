package ru.denfad.UrlShortener.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "urls")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url", nullable = false)
    private String url;

    public UrlModel(String url) {
        this.url = url;
    }

    public UrlModel() {
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
}
