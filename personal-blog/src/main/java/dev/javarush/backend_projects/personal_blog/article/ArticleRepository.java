package dev.javarush.backend_projects.personal_blog.article;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ArticleRepository extends CrudRepository<Article, ArticleId>,
    PagingAndSortingRepository<Article, ArticleId>, ArticleRepositoryCustom {
}
