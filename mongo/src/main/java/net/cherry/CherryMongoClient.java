package net.cherry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import net.cherry.client.CherryClient;
import net.cherry.query.Query;
import net.cherry.serialization.EntityDeserializer;
import net.cherry.serialization.EntitySerializer;
import net.cherry.serialization.MongoEntityDeserializer;
import net.cherry.serialization.MongoEntitySerializer;
import org.bson.conversions.Bson;

public class CherryMongoClient implements CherryClient {
    private final String uri;
    private final String databaseName;

    private MongoClient client;
    private MongoDatabase database;

    private static final EntitySerializer SERIALIZER = new MongoEntitySerializer();
    private static final EntityDeserializer DESERIALIZER = new MongoEntityDeserializer();

    public CherryMongoClient(String uri, String databaseName) {
        this.uri = uri;
        this.databaseName = databaseName;
    }

    @Override
    public void connect() {
        try (final MongoClient client = MongoClients.create(this.uri)) {
            this.client = client;
            this.database = client.getDatabase(this.databaseName);
        }
    }

    @Override
    public <T> T create(T proxiedEntity) {
        this.validateConnection();

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

    public static Bson queryToMongoQuery(Query query) {
        final Set<Bson> subQueries = new HashSet<>();

        return Filters.and(subQueries);
    }

    private void validateConnection() {
        if (this.client == null) {
            throw new IllegalStateException("A MongoDB connection has not been established.");
        }
    }
}
