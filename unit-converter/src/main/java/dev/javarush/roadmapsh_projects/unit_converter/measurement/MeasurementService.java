package dev.javarush.roadmapsh_projects.unit_converter.measurement;

import org.springframework.stereotype.Service;

@Service
public class MeasurementService {
  public double convertMeasurement(
      Measurement measurement,
      double amount,
      Unit fromUnit,
      Unit toUnit
  ) {
    if (!measurement.hasUnit(fromUnit)) {
      throw new IllegalArgumentException("Unit " + fromUnit.getName() + " is not related to measurement " + measurement.getName());
    }
    if (!measurement.hasUnit(toUnit)) {
      throw new IllegalArgumentException("Unit " + toUnit.getName() + " is not related to measurement " + measurement.getName());
    }
    double amountInDefaultUnit = fromUnit.convertToDefaultUnit(amount);
    return toUnit.convertFromDefaultUnit(amountInDefaultUnit);
  }
}
