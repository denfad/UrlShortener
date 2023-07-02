package ru.denfad.UrlShortener.dto;

public class UrlStatistic {
    private String url;
    private String shortUrl;
    private int redirects;
    private int place;

    public UrlStatistic(String url, String shortUrl, int redirects, int place) {
        this.url = url;
        this.shortUrl = shortUrl;
        this.redirects = redirects;
        this.place = place;
    }

    public UrlStatistic() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getRedirects() {
        return redirects;
    }

    public void setRedirects(int redirects) {
        this.redirects = redirects;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
