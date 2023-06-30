package ru.denfad.UrlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.repository.UrlMongoRepository;
import ru.denfad.UrlShortener.repository.UrlRepository;
import ru.denfad.UrlShortener.repository.UrlRepositoryImpl;

@Service
public class UrlService {

    private final UrlMongoRepository mongoRepository;

    private final UrlRepository urlRepository;


    @Autowired
    public UrlService(UrlMongoRepository mongoRepository, UrlRepository urlRepository) {
        this.mongoRepository = mongoRepository;
        this.urlRepository = urlRepository;
    }

    public String saveUrl(String url) {
        UrlDocument u = mongoRepository.save(new UrlDocument(url));
        return Base62Coder.encode(u.getId());
    }

    public String redirect(String shortUrl){
        int id = Base62Coder.decode(shortUrl);
        String url =  urlRepository.readAndUpdateUrl(id).getUrl();
        return url;

    }
}
