package com.cartrawler.assessment.processor;

import com.cartrawler.assessment.config.ProcessingOptions;
import com.cartrawler.assessment.model.CarResult;
import com.cartrawler.assessment.service.strategy.FilterDuplicatesFactory;
import com.cartrawler.assessment.service.strategy.FilterDuplicatesStrategy;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class FilterDuplicatesProcessor implements CarResultsProcessor {

  private static final class GroupingKey {
    String description;
    String supplierName;
    String sippCode;
    CarResult.FuelPolicy fuelPolicy;

    public GroupingKey(String description, String supplierName, String sippCode, CarResult.FuelPolicy fuelPolicy) {
      this.description = description;
      this.supplierName = supplierName;
      this.sippCode = sippCode;
      this.fuelPolicy = fuelPolicy;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof FilterDuplicatesProcessor.GroupingKey that)) return false;
      return
        Objects.equals(description, that.description) &&
          Objects.equals(supplierName, that.supplierName) &&
          Objects.equals(sippCode, that.sippCode) &&
          fuelPolicy == that.fuelPolicy;
    }

    @Override
    public int hashCode() {
      return Objects.hash(description, supplierName, sippCode, fuelPolicy);
    }
  }

  private final ProcessingOptions processingOptions;

  public FilterDuplicatesProcessor(ProcessingOptions processingOptions) {
    this.processingOptions = processingOptions;
  }

  @Override
  public List<CarResult> processCarResults(List<CarResult> carResults) {
    FilterDuplicatesStrategy strategy = FilterDuplicatesFactory.getStrategy(this.processingOptions.getFilterDuplicatesStrategyName());
    Map<FilterDuplicatesProcessor.GroupingKey, List<CarResult>> groups =
      carResults
        .stream()
        .collect(groupingBy(post ->
          new FilterDuplicatesProcessor.GroupingKey(
            post.getDescription(),
            post.getSupplierName(),
            post.getSippCode(),
            post.getFuelPolicy())));
    return
      groups.values().stream()
        .map(carResultListPerGroup -> strategy.filterDuplicates(carResultListPerGroup))
        .collect(Collectors.toList());
  }
}
