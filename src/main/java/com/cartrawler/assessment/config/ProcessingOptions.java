package com.cartrawler.assessment.config;

import com.cartrawler.assessment.service.strategy.FilterDuplicatesStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ProcessingOptions {

  private FilterDuplicatesStrategy.Name filterDuplicatesStrategyName = FilterDuplicatesStrategy.Name.MIN;
  private boolean removeAboveMedian = false;

  public static ProcessingOptions load(String path) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(
      ProcessingOptions.class.getClassLoader().getResource(path),
      ProcessingOptions.class);
  }

  public ProcessingOptions() {}

  public ProcessingOptions(FilterDuplicatesStrategy.Name filterDuplicatesStrategyName, boolean removeAboveMedian) {
    this.filterDuplicatesStrategyName = filterDuplicatesStrategyName;
    this.removeAboveMedian = removeAboveMedian;
  }

  public FilterDuplicatesStrategy.Name getFilterDuplicatesStrategyName() {
    return filterDuplicatesStrategyName;
  }

  public boolean isRemoveAboveMedian() {
    return removeAboveMedian;
  }

  @Override
  public String toString() {
    return "ProcessingOptions{" +
        "filterDuplicatesStrategyName=" + filterDuplicatesStrategyName +
        ", removeAboveMedian=" + removeAboveMedian +
        '}';
  }
}
