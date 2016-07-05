package io.github.tjheslin1.esb.infrastructure.application.cqrs.query;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceCommandReader;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import org.bson.Document;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

public class MongoBalanceCommandReader implements BalanceCommandReader {

    private final MongoClient mongoClient;
    private MongoSettings mongoSettings;

    public MongoBalanceCommandReader(MongoClient mongoClient, MongoSettings mongoSettings) {
        this.mongoClient = mongoClient;
        this.mongoSettings = mongoSettings;
    }

    @Override
    public Stream<BalanceCommand> retrieveSortedEvents(int accountId, EventWiring eventWiring) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(mongoSettings.mongoDbName());

        FindIterable<Document> events = eventStoreDb.getCollection(eventWiring.collectionName())
                .find(
                        eq("accountId", accountId))
                .sort(Sorts.ascending("timeOfEvent"));

        return StreamSupport.stream(events.spliterator(), false).map(eventWiring.unmarshaller()::unmarshallBalanceEvent);
    }
}
