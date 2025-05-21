package com.cartrawler.assessment.processor;

import com.cartrawler.assessment.config.ProcessingOptions;
import com.cartrawler.assessment.model.CarResult;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveAboveMedianProcessor implements CarResultsProcessor {

  private final ProcessingOptions processingOptions;

  public RemoveAboveMedianProcessor(ProcessingOptions processingOptions) {
    this.processingOptions = processingOptions;
  }

  @Override
  public List<CarResult> processCarResults(List<CarResult> carResults) {
    if (!this.processingOptions.isRemoveAboveMedian()) {
      return carResults;
    }
    List<CarResult> corporate =
      removeAboveMedian(
        carResults.stream()
          .filter(CarResult::isCorporate)
          .collect(Collectors.toList()));
    List<CarResult> nonCorporate =
      removeAboveMedian(
        carResults.stream()
          .filter(carResult -> !carResult.isCorporate())
          .collect(Collectors.toList()));
    List<CarResult> result = new ArrayList<>(corporate);
    result.addAll(nonCorporate);
    return result;
  }

  protected List<CarResult> removeAboveMedian(List<CarResult> carResults) {
    List<CarResult> sortedCarResults = new ArrayList<>(carResults);
    sortedCarResults.sort(Comparator.comparingDouble(CarResult::getRentalCost));
    int size = sortedCarResults.size();
    double median = (size % 2 == 0) ?
      (sortedCarResults.get(size / 2 - 1).getRentalCost() + sortedCarResults.get(size / 2).getRentalCost()) / 2 :
      sortedCarResults.get(size / 2).getRentalCost();
    return carResults.stream()
      .filter(carResult -> carResult.getRentalCost() <= median)
      .collect(Collectors.toList());
  }
}
