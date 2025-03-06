package dev.javarush.roadmapsh_projects.blogging_platform_api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
