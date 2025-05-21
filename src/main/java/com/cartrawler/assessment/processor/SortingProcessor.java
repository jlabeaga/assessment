package com.cartrawler.assessment.processor;

import com.cartrawler.assessment.model.CarResult;

import java.util.Comparator;
import java.util.List;

public class SortingProcessor implements CarResultsProcessor {

  @Override
  public List<CarResult> processCarResults(List<CarResult> carResults) {
    carResults.sort(
      Comparator
        .comparing(CarResult::isCorporate, Comparator.reverseOrder())
        .thenComparing(CarResult::getSippType)
        .thenComparing(CarResult::getRentalCost)
        .thenComparing(CarResult::getSupplierName)
    );
    return carResults;
  }
}
