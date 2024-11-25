package dev.javarush.roadmapsh_projects.unit_converter.web;

import dev.javarush.roadmapsh_projects.unit_converter.measurement.Measurement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnitConverterController {
  @GetMapping
  public String index(Model model) {
    model.addAttribute("measurements", Measurement.values());
    return "index";
  }
}
