package dev.javarush.roadmapsh_projects.blogging_platform_api.blog;

import dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select new dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent" +
            "(post.id, post.title, post.tags, post.category, post.createdAt, post.updatedAt) from Post post")
    Collection<PostWithoutContent> findAllWithoutContent();
}
