package dev.javarush.backend_projects.personal_blog.configuration;

import dev.javarush.backend_projects.personal_blog.db.InMemoryUniqueIdGenerator;
import dev.javarush.backend_projects.personal_blog.db.UniqueIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

  @Bean
  public UniqueIdGenerator uniqueIdGenerator() {
    return new InMemoryUniqueIdGenerator();
  }
}
