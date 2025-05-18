package dev.javarush.roadmapsh_projects.todo_list_api.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("demo")
public class DemoController {
    @GetMapping
    public Map<String, String> hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Map.of("message", "Hello, " + authentication.getPrincipal());
    }
}
