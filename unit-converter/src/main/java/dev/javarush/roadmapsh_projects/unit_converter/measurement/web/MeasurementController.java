package dev.javarush.roadmapsh_projects.unit_converter.measurement.web;

import dev.javarush.roadmapsh_projects.unit_converter.measurement.Measurement;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MeasurementController {
  @GetMapping("measurement-form/{measurement}")
  public String measurementForm(
      @PathVariable("measurement") Measurement measurement,
      Model model
  ) {
    model.addAttribute("measurement", measurement);
    return "measurement-form";
  }
}
