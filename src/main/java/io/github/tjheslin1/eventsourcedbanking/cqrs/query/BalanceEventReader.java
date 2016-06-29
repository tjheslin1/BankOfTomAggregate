package io.github.tjheslin1.eventsourcedbanking.cqrs.query;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.EventWiring;
import io.github.tjheslin1.settings.MongoSettings;
import org.bson.Document;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

public class BalanceEventReader {

    private final MongoClient mongoClient;
    private MongoSettings mongoSettings;

    public BalanceEventReader(MongoClient mongoClient, MongoSettings mongoSettings) {
        this.mongoClient = mongoClient;
        this.mongoSettings = mongoSettings;
    }

    public Stream<BalanceEvent> retrieveSorted(int accountId, EventWiring eventWiring) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(mongoSettings.mongoDbName());

        FindIterable<Document> events = eventStoreDb.getCollection(eventWiring.collectionName())
                .find(
                        eq("accountId", accountId))
                .sort(Sorts.ascending("timeOfEvent"));

        return StreamSupport.stream(events.spliterator(), false).map(eventWiring.unmarshaller()::unmarshallBalanceEvent);
    }
}
