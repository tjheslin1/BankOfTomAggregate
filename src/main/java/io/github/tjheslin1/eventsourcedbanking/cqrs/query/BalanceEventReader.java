package io.github.tjheslin1.eventsourcedbanking.cqrs.query;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

public class BalanceEventReader {

    private final MongoClient mongoClient;
    private Settings settings;

    public BalanceEventReader(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public List<BalanceEvent> retrieveSorted(int accountId, BalanceEventJsonUnmarshaller jsonUnmarshaller, String collectionName) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

        FindIterable<Document> events = eventStoreDb.getCollection(collectionName)
                .find(
                        eq("accountId", accountId))
                .sort(Sorts.ascending("timeOfEvent"));

        List<BalanceEvent> sortedEvents = new ArrayList();
        StreamSupport.stream(events.spliterator(), false).forEach(event -> sortedEvents.add(jsonUnmarshaller.unmarshallBalanceEvent(event)));
        return sortedEvents;
    }
}
