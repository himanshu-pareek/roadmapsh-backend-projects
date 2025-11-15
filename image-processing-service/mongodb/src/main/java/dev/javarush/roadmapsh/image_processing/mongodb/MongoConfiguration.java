package dev.javarush.roadmapsh.image_processing.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Objects;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfiguration {

  @Value("${mongo.connection-string}")
  private String connectionString;

  MongoClient mongoClient() {
    return MongoClients.create(
        MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .build()
    );
  }

  @Bean
  MongoDatabaseFactory mongoDatabaseFactory() {
    return new SimpleMongoClientDatabaseFactory(
        mongoClient(),
        Objects.requireNonNull(new ConnectionString(connectionString).getDatabase())
    );
  }
}
