package dev.javarush.roadmapsh.image_processing.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
class MongoImageRepository implements ImageRepository {

  private final MongoTemplate template;

  MongoImageRepository(MongoDatabaseFactory factory) {
    this.template = new MongoTemplate(factory);
  }

  @Override
  public Image insert(Image image) {
    return this.template.insert(image);
  }

  @Override
  public Image findById(String id) {
    return this.template.findById(id, Image.class);
  }

  @Override
  public Collection<Image> findByOwner(String username) {
    return this.template.query(Image.class)
        .matching(query(where("owner").is(username)))
        .all();
  }

  @Override
  public void update(Image image) {
    this.template.save(image);
  }

  @Override
  public String getNewId() {
    while (true) {
      String id = UUID.randomUUID().toString();
      Image image = this.template.findById(id, Image.class);
      if (image == null) {
        return id;
      }
    }
  }
}
