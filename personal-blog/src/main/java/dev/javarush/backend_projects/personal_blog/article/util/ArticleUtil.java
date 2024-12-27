package dev.javarush.backend_projects.personal_blog.article.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ArticleUtil {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }
}
