package net.cherry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import net.cherry.client.CherryClient;
import net.cherry.codec.Codec;
import net.cherry.config.CodecConfig;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.entity.EntitySettings;
import net.cherry.query.Query;
import net.cherry.serialization.MongoEntityDeserializer;
import net.cherry.serialization.MongoEntitySerializer;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

public class CherryMongoClient implements CherryClient {
    private final String uri;
    private final String databaseName;

    private MongoClient client;
    private MongoDatabase database;
    private CodecRegistry codecRegistry;

    public static final MongoEntitySerializer SERIALIZER = new MongoEntitySerializer();
    public static final MongoEntityDeserializer DESERIALIZER = new MongoEntityDeserializer();

    public CherryMongoClient(String uri, String databaseName) {
        this.uri = uri;
        this.databaseName = databaseName;
    }

    @Override
    public void connect() {
        this.client = MongoClients.create(this.uri);
        this.database = this.client.getDatabase(this.databaseName);
        this.codecRegistry = this.database.getCodecRegistry();
    }

    @Override
    public <T extends Entity<T>> T save(T proxiedEntity) {
        this.validateConnection();

        final EntityController<T> controller = EntityControllerManager.getController(proxiedEntity);
        final EntitySettings<T> settings = controller.getSettings();
        final MongoCollection<Document> collection = this.database.getCollection(settings.getDatabase());

        final Document document = this.bsonDocumentToDocument(SERIALIZER.serialize(proxiedEntity));
        collection.insertOne(document);

        return proxiedEntity;
    }

    @Override
    public <T> Collection<T> findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        this.validateConnection();

        final Query query = this.handleQueryConsumer(queryConsumer);

        return null;
    }

    @Override
    public <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        this.validateConnection();

        final Query query = this.handleQueryConsumer(queryConsumer);

        return null;
    }

    @Override
    public <T> int count(Class<T> identifier, Consumer<Query> queryConsumer) {
        this.validateConnection();

        final Query query = this.handleQueryConsumer(queryConsumer);

        return 0;
    }

    @Override
    public Collection<Codec<?, ?>> provideCodecs() {
        return CodecConfig.MONGO_CODECS;
    }

    public static Bson queryToMongoQuery(Query query) {
        final Set<Bson> subQueries = new HashSet<>();

        return Filters.and(subQueries);
    }

    private void validateConnection() {
        if (this.client == null) {
            throw new IllegalStateException("A MongoDB connection has not been established.");
        }
    }

    private Document bsonDocumentToDocument(BsonDocument document) {
        return this.codecRegistry.get(Document.class).decode(document.asBsonReader(), DecoderContext.builder().build());
    }
}
