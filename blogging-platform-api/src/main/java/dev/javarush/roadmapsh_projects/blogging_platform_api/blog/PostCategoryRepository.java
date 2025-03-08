package dev.javarush.roadmapsh_projects.blogging_platform_api.blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer> {
}
