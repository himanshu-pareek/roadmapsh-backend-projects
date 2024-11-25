package dev.javarush.roadmapsh_projects.unit_converter.measurement.web;

import dev.javarush.roadmapsh_projects.unit_converter.measurement.Measurement;
import dev.javarush.roadmapsh_projects.unit_converter.measurement.MeasurementService;
import dev.javarush.roadmapsh_projects.unit_converter.measurement.Unit;
import io.micrometer.common.util.StringUtils;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("convert")
public class ConversionController {

  private final MeasurementService measurementService;

  public ConversionController(MeasurementService measurementService) {
    this.measurementService = measurementService;
  }

  @PostMapping("{measurement}")
  public String convert(
      @PathVariable("measurement")Measurement measurement,
      @RequestParam Map<String, String> requestParams,
      Model model
  ) {
    String amountString = requestParams.get("amount");
    double amount = StringUtils.isBlank(amountString) ? 0.0 : Double.parseDouble(amountString);
    Unit fromUnit = Unit.valueOf(requestParams.get("fromUnit"));
    Unit toUnit = Unit.valueOf(requestParams.get("toUnit"));
    double convertedAmount = this.measurementService.convertMeasurement(
        measurement,
        amount,
        fromUnit,
        toUnit
    );
    model.addAttribute("fromUnit", fromUnit);
    model.addAttribute("toUnit", toUnit);
    model.addAttribute("fromAmount", amount);
    model.addAttribute("toAmount", convertedAmount);
    model.addAttribute("measurement", measurement);
    return "convert-result";
  }
}
