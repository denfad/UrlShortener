package ru.denfad.UrlShortener.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;


public class GenerateDTO {
    @NotNull(message = "Url cannot be null")
    @NotBlank(message = "Url cannot be empty")
    @URL(message = "Url must be a valid")
    private String url;

    public GenerateDTO(String url) {
        this.url = url;
    }

    public GenerateDTO(String host, String link) {
        this.url = host + link;
    }

    public GenerateDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
