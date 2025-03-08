package dev.javarush.roadmapsh_projects.blogging_platform_api.blog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "post_categories")
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_categories_id_seq_generator")
    @SequenceGenerator(name = "post_categories_id_seq_generator", sequenceName = "post_categories_id_seq", allocationSize = 1)

    private Integer id;

    @Column(nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    public PostCategory() {}

    public PostCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public PostCategory(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
