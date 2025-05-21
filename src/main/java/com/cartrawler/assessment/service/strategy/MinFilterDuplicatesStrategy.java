package com.cartrawler.assessment.service.strategy;

import com.cartrawler.assessment.model.CarResult;

import java.util.Comparator;
import java.util.List;

public class MinFilterDuplicatesStrategy implements FilterDuplicatesStrategy {

  @Override
  public CarResult filterDuplicates(List<CarResult> carResults) {
    return
      carResults.stream()
      .min(Comparator.comparingDouble(CarResult::getRentalCost))
      .orElse(null);
  }
}
