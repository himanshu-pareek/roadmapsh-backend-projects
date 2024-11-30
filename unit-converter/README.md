# Unit Converter

[Project URL](https://roadmap.sh/projects/unit-converter)

## Running the project

1. Clone the GitHub repository:
   ```shell
   git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
   
2. Go to `unit-converter` directory:
    ```shell
    cd roadmap-backend-projects
    cd unit-converter
    ```

3. Run the project using **Gradle**:
    ```shell
    ./gradlew bootRun
    ```
   
## Configuring the Measurements and units

### Terminology

1. Measurement - which can be measured. For example, length, weight, temperature, time.
2. Unit - Unit of measurement. For example, meter, centimeter, Ounce, Seconds, Kelvin.

### Configuring available units

Modify the list of enums present in `dev.javarush.roadmapsh_projects.unit_converter.measurement.Unit` enum.

You need to supply `4` parameters to create a `Unit` enum.

1. `name` - Name of Unit (Ex - Meter, Centimeter, Kelvin). This is visible in UI.
2. `suffix` - Suffix used to represent a measurement. For example in measurement `2 m`, the suffix is `m`. This is visible in UI.
3. `toDefault` - Lambda function, that can be used to convert certain amount of current unit to default unit's amount. The default unit for each measurement should be decided beforehand. For example, we may consider **Meter** as default unit for `length`. And while creating **Centimeter** unit for length, we can specify `x -> 0.01 * x` as `toDefault` for `CENTIMETER` enum.
4. `fromDefault` - Inverse of `toDefault`.

## Configuring available measurements

Modify the list of enums present in `dev.javarush.roadmapsh_projects.unit_converter.measurement.Measurement` enum.

You need to supply `3` parameters to create a `Measurement` enum.

1. `name` - Name of measurement (Ex - Length, Weight, Temperature). This is visible in UI.
2. `instruction` - Instruction to user to enter the amount of measurement. For example, `Please enter length to convert`.
3. `units` - List of units available for this measurement.

> Look at existing examples of Units and Measurements for reference.
