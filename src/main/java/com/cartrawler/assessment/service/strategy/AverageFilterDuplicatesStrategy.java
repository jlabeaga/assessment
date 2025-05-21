package com.cartrawler.assessment.service.strategy;

import com.cartrawler.assessment.model.CarResult;

import java.util.List;

public class AverageFilterDuplicatesStrategy implements FilterDuplicatesStrategy {

  @Override
  public CarResult filterDuplicates(List<CarResult> carResults) {
    double avgRentalCost = carResults.stream()
      .mapToDouble(CarResult::getRentalCost)
      .average()
      .orElse(0.0);
    return
      new CarResult(
        carResults.get(0).getDescription(),
        carResults.get(0).getSupplierName(),
        carResults.get(0).getSippCode(),
        avgRentalCost,
        carResults.get(0).getFuelPolicy());
  }
}
