package ru.denfad.UrlShortener.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.denfad.UrlShortener.dto.UrlStatistic;
import ru.denfad.UrlShortener.exception.UrlNotFoundException;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.repository.UrlMongoRepository;
import ru.denfad.UrlShortener.repository.UrlRepository;
import ru.denfad.UrlShortener.coder.Base62Coder;
import ru.denfad.UrlShortener.service.StatsService;
import ru.denfad.UrlShortener.service.UrlService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public String redirect(String shortUrl) {
        int id = Base62Coder.decode(shortUrl);
        try {
            String url = urlRepository.readAndUpdateUrl(id).getUrl();
            return url;
        }
        catch (NullPointerException e){
            throw new UrlNotFoundException(String.format("link /%s not found", shortUrl));
        }
    }

    public List<UrlStatistic> getStatsPage(int page, int size) {
        Page<UrlDocument> docPage = mongoRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "redirects"));
        return getUrlStatsFromPage(docPage);
    }

    private List<UrlStatistic> getUrlStatsFromPage(Page<UrlDocument> page) {
        int i = page.getPageable().getPageNumber() * page.getSize() + 1;
        List<UrlStatistic> stats = new ArrayList<>();
        for (UrlDocument doc : page.getContent()) {
            stats.add(new UrlStatistic(doc.getUrl(), Base62Coder.encode(doc.getId()), doc.getRedirects(), i));
            ++i;
        }
        return stats;
    }
}
