package com.cartrawler.assessment.service.strategy;

import java.util.HashMap;
import java.util.Map;

public class FilterDuplicatesFactory {

  public static FilterDuplicatesStrategy.Name DEFAULT_STRATEGY = FilterDuplicatesStrategy.Name.MIN;

  private static final Map<FilterDuplicatesStrategy.Name, FilterDuplicatesStrategy> STRATEGIES = new HashMap<>();
  static {
   STRATEGIES.put(FilterDuplicatesStrategy.Name.MIN, new MinFilterDuplicatesStrategy());
   STRATEGIES.put(FilterDuplicatesStrategy.Name.MAX, new MaxFilterDuplicatesStrategy());
   STRATEGIES.put(FilterDuplicatesStrategy.Name.AVERAGE, new AverageFilterDuplicatesStrategy());
  }

  public static FilterDuplicatesStrategy getStrategy(FilterDuplicatesStrategy.Name strategy) {
    if (strategy == null || !STRATEGIES.containsKey(strategy)) {
      return STRATEGIES.get(DEFAULT_STRATEGY);
    }
    return STRATEGIES.get(strategy);
  }
}
