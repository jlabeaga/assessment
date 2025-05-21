package com.cartrawler.assessment.service.strategy;

import com.cartrawler.assessment.model.CarResult;

import java.util.Comparator;
import java.util.List;

public class MaxFilterDuplicatesStrategy implements FilterDuplicatesStrategy {

  @Override
  public CarResult filterDuplicates(List<CarResult> carResults) {
    return
      carResults.stream()
      .max(Comparator.comparingDouble(CarResult::getRentalCost))
      .orElse(null);
  }
}
