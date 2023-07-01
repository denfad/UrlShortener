package ru.denfad.UrlShortener.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.repository.UrlMongoRepository;
import ru.denfad.UrlShortener.repository.UrlRepository;
import ru.denfad.UrlShortener.coder.Base62Coder;
import ru.denfad.UrlShortener.service.StatsService;
import ru.denfad.UrlShortener.service.UrlService;

import java.time.Instant;
import java.util.Date;

@Service
public class UrlServiceImpl implements UrlService, StatsService {

    private final UrlMongoRepository mongoRepository;

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlMongoRepository mongoRepository, UrlRepository urlRepository) {
        this.mongoRepository = mongoRepository;
        this.urlRepository = urlRepository;
    }

    public String saveUrl(String url) {
        UrlDocument urlDocument = new UrlDocument(url, Date.from(Instant.now()));
        mongoRepository.save(urlDocument);
        return Base62Coder.encode(urlDocument.getId());
    }

    public String redirect(String shortUrl){
        int id = Base62Coder.decode(shortUrl);
        return urlRepository.readAndUpdateUrl(id).getUrl();
    }

    public Page<UrlDocument> getStatsPage(int page, int size) {
       return mongoRepository.findAll(PageRequest.of(page,size, Sort.Direction.DESC, "redirects"));
    }
}
