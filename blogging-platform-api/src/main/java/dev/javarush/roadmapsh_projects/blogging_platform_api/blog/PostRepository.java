package dev.javarush.roadmapsh_projects.blogging_platform_api.blog;

import dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(
        "select new dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent" +
            "(post.id, post.title, post.tags, post.category, post.createdAt, post.updatedAt) " +
            "from Post post"
    )
    Collection<PostWithoutContent> findAllWithoutContent();

    @Query(
        "select new dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent" +
            "(post.id, post.title, post.tags, post.category, post.createdAt, post.updatedAt) " +
            "from Post post " +
            "where post.title like %?1% or post.content like %?1% or post.category.name like %?1%"
    )
    Collection<PostWithoutContent> searchForPostsUsingTerm(String term);
}
