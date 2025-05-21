package com.cartrawler.assessment.service.strategy;

import com.cartrawler.assessment.model.CarResult;

import java.lang.FunctionalInterface;
import java.util.List;

@FunctionalInterface
public interface FilterDuplicatesStrategy {

  enum Name {
    MIN,
    MAX,
    AVERAGE
  }

  CarResult filterDuplicates(List<CarResult> carResults);
}
