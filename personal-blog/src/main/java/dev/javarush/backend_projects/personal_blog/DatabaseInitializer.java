package dev.javarush.backend_projects.personal_blog;

import com.github.javafaker.Faker;
import dev.javarush.backend_projects.personal_blog.article.Article;
import dev.javarush.backend_projects.personal_blog.article.ArticleService;
import dev.javarush.backend_projects.personal_blog.article.CreateArticleParameters;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("init-db")
public class DatabaseInitializer implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

  private final ArticleService articleService;
  private final Faker faker;
  private final Random random;

  public DatabaseInitializer(ArticleService articleService) {
    this.articleService = articleService;
    this.faker = new Faker();
    this.random = new Random();
  }

  @Override
  public void run(String... args) throws Exception {
    LOGGER.info("Initializing database...");
    for (int i = 0; i < 30; i++) {
      CreateArticleParameters parameters = generateRandomCreateArticleParameters();
      Article article = this.articleService.createArticle(parameters);
      LOGGER.info("Article added {} - {}", article.getId(), article.getTitle());
    }
    LOGGER.info("Database initialization complete ✅");
  }

  private CreateArticleParameters generateRandomCreateArticleParameters() {
    String title = generateRandomArticleTitle();
    LocalDate publishDate = generateRandomPublishDate();
    String content = generateRandomArticleContent();
    return new CreateArticleParameters(title, publishDate, content);
  }

  private String generateRandomArticleContent() {
    return faker.lorem().paragraphs(random.nextInt(5, 10))
        .stream()
        .reduce("", (a, b) -> a + "\n" + b);
  }

  private LocalDate generateRandomPublishDate() {
    Date date = faker.date().past(1500, TimeUnit.DAYS);
    return LocalDate.of(date.getYear(), date.getMonth() + 1, date.getDate());
  }

  private String generateRandomArticleTitle() {
    return faker.lorem().sentence(5, 4);
  }
}
