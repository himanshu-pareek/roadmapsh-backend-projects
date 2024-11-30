package dev.javarush.roadmapsh_projects.unit_converter.measurement;

import java.util.List;

public enum Measurement {
  LENGTH(
      // Default unit - Meter
      "Length",
      "Please enter length",
      List.of(
          Unit.METER,
          Unit.CENTIMETER,
          Unit.KILOMETER,
          Unit.MILLIMETER,
          Unit.INCH,
          Unit.FEET,
          Unit.MILE,
          Unit.YARD
      )
  ),
  WEIGHT(
      // Default unit - Gram
      "Weight",
      "Please enter weight to convert",
      List.of(
          Unit.GRAM,
          Unit.KILOGRAM,
          Unit.OUNCE,
          Unit.POUND
      )
  );

  private final String name;
  private final String instruction;
  private final List<Unit> units;

  Measurement(String name, String instruction, List<Unit> units) {
    this.name = name;
    this.instruction = instruction;
    this.units = units;
  }

  public String getName() {
    return name;
  }

  public String getInstruction() {
    return instruction;
  }

  public List<Unit> getUnits() {
    return units.stream().toList();
  }

  public boolean hasUnit(Unit unit) {
    return this.units.contains(unit);
  }
}
