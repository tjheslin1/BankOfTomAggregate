package io.github.tjheslin1.learning;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// http://mongodb.github.io/mongo-java-driver/2.13/getting-started/quick-tour/
public class EmbeddedMongoTest implements WithAssertions, WithMockito {

    private static final String DATABASE_NAME = "embedded";

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoClient mongo;

    private Settings settings = mock(Settings.class);

    @Before
    public void beforeEach() throws Exception {
        when(settings.mongoDbPort()).thenReturn(27017);

        MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.V3_2_1)
                .net(new Net(settings.mongoDbPort(), Network.localhostIsIPv6())).build();
        mongodExe = mongodStarter.prepare(mongodConfig);
        mongod = mongodExe.start();
        mongo = new MongoClient("localhost", settings.mongoDbPort());
    }

    @After
    public void afterEach() throws Exception {
        if (this.mongod != null) {
            this.mongod.stop();
            this.mongodExe.stop();
        }
    }

    @Ignore
    @Test
    public void shouldCreateNewObjectInEmbeddedMongoDbv3() {
        MongoDatabase db = mongo.getDatabase(DATABASE_NAME);
        db.createCollection("testCollection", createCollectionOptions());

        MongoCollection<Document> coll = db.getCollection("testCollection");

        Document doc = new Document("restaurants", new Document("restaurant", "test"));
        coll.insertOne(doc);

        assertThat(db.getCollection("testCollection").count()).isEqualTo(1);
    }

    private CreateCollectionOptions createCollectionOptions() {
        CreateCollectionOptions collectionOptions = new CreateCollectionOptions();
        // options
        return collectionOptions;

    }
}