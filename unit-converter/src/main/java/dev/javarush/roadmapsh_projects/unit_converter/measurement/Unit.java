package dev.javarush.roadmapsh_projects.unit_converter.measurement;

import java.util.function.Function;

public enum Unit {
  METER("Meter", "m", Function.identity(), Function.identity()),
  CENTIMETER("Centimeter", "cm", x -> x * 0.01, x -> 100 * x),
  MILLIMETER("Millimeter", "mm", x -> x * 0.001, x -> 1000 * x),
  KILOMETER("Kilometer", "km", x -> 1000 * x, x -> 0.001 * x),
  INCH("Inches", "inch", x -> 0.0254 * x, x -> 39.37008 * x),
  FEET("Feet", "feet", x -> 0.3048 * x, x -> 3.28084 * x),
  MILE("Mile", "mile", x -> 1609.344 * x, x -> 0.0006213712 * x),
  YARD("Yard", "yard", x -> 0.9144 * x, x -> 1.093613 * x),

  GRAM("Gram", "gm.", Function.identity(), Function.identity()),
  KILOGRAM("Killo gram", "Kg.", x -> 1000 * x, x -> 0.001 * x),
  OUNCE("Ounce", "ounce", x -> 28.34952 * x, x -> 0.03527396 * x),
  POUND("Pound", "pound", x -> 453.5924 * x, x -> 0.002204623 * x);

  private final String name;
  private final String suffix;
  private final Function<Double, Double> toDefault;
  private final Function<Double, Double> fromDefault;

  Unit(String name, String suffix, Function<Double, Double> toDefault,
       Function<Double, Double> fromDefault) {
    this.name = name;
    this.suffix = suffix;
    this.toDefault = toDefault;
    this.fromDefault = fromDefault;
  }

  public String getName() {
    return name;
  }

  public String getSuffix() {
    return suffix;
  }

  public Double convertToDefaultUnit(Double value) {
    return toDefault.apply(value);
  }

  public Double convertFromDefaultUnit(Double value) {
    return fromDefault.apply(value);
  }
}
