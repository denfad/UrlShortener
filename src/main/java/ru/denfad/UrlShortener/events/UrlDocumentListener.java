package ru.denfad.UrlShortener.events;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.denfad.UrlShortener.model.UrlDocument;
import ru.denfad.UrlShortener.service.IdentifierGenerator;
import ru.denfad.UrlShortener.service.impl.SequenceGenerator;

@Component
public class UrlDocumentListener extends AbstractMongoEventListener<UrlDocument> {

    private IdentifierGenerator sequenceGenerator;

    @Autowired
    public UrlDocumentListener(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UrlDocument> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateIdentifier(UrlDocument.SERVER_NAME));
        }
    }


}
