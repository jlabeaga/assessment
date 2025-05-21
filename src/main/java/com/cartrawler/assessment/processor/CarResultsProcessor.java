package com.cartrawler.assessment.processor;

import com.cartrawler.assessment.model.CarResult;

import java.util.List;

public interface CarResultsProcessor {

  List<CarResult> processCarResults(List<CarResult> carResults);

}
