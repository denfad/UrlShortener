package ru.denfad.UrlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denfad.UrlShortener.model.UrlModel;
import ru.denfad.UrlShortener.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository repository;

    @Autowired
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String saveUrl(UrlModel url) {
        repository.save(url);
        return Base62Coder.encode(url.getId());
    }

    public String redirect(String shortUrl){
        int id = Base62Coder.decode(shortUrl);

        return repository.findById(id).orElseThrow().getUrl();

    }
}
