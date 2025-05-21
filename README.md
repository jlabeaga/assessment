# CarTrawler Assessment

Technical assessment for Cartrawler

> A CLI tool that filters and rearrange data and displays the result in the system console.

---

## 📦 Features

- Reads a fixed set of data.
- Supports configurable processing via json configuration file `src/main/resources/processingOptions.json`.

---

### ✅ Prerequisites

- Java 17+ (JDK)

### 📥 Installation

Install using the maven wrapper:

```bash
./mvnw clean install
````

```ms-dos
mvnw.cmd clean install
````

Execute the app for different combinations of the variables inside `src/main/resources/processingOptions.json`:

- `filterDuplicatesStrategyName`: Values = MIN | MAX | AVERAGE
- `removeAboveMedian`: Values = true | false

Meaning:

`filterDuplicatesStrategyName` indicates how we choose/discard duplicated elements (same make, model, supplier, SIPP, FuelPolicy):
- MIN: we keep the element with the minimum rentalCost and discard the rest. 
- MAX: we keep the element with the maximum rentalCost and discard the rest. 
- AVERAGE: we assign the average rentalCost to the selected element and discard the rest.

`removeAboveMedian` indicates:
- true: we discard all the elements with rentalCost above the median value for its group (Corporate/NonCorporate) at the last processing step
- false: we don't discard anything

Sample execution:

```bash
./mvnw exec:java 
```

```ms-dos
mvnw.cmd exec:java 
```

