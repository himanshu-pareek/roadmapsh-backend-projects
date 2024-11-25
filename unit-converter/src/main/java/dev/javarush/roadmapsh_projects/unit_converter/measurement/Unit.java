package dev.javarush.roadmapsh_projects.unit_converter.measurement;

import java.util.function.Function;

public enum Unit {
  METER("Meter", "m.", Function.identity(), Function.identity()),
  INCH("Inch", "in.", x -> 0.0254 * x, x -> 39.37008 * x),
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
