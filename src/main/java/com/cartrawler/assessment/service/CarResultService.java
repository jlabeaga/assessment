package com.cartrawler.assessment.service;

import com.cartrawler.assessment.model.CarResult;
import com.cartrawler.assessment.processor.FilterDuplicatesProcessor;
import com.cartrawler.assessment.processor.RemoveAboveMedianProcessor;
import com.cartrawler.assessment.processor.SortingProcessor;
import com.cartrawler.assessment.repository.CarResultRepository;

import java.util.List;

public class CarResultService {

  private final CarResultRepository carResultRepository;
  private final DisplayService displayService;
  private final FilterDuplicatesProcessor filterDuplicatesProcessor;
  private final SortingProcessor sortingProcessor;
  private final RemoveAboveMedianProcessor removeAboveMedianProcessor;

  public CarResultService(
    CarResultRepository carResultRepository,
    DisplayService displayService,
    FilterDuplicatesProcessor filterDuplicatesProcessor,
    SortingProcessor sortingProcessor,
    RemoveAboveMedianProcessor removeAboveMedianProcessor) {

    this.carResultRepository = carResultRepository;
    this.displayService = displayService;
    this.filterDuplicatesProcessor = filterDuplicatesProcessor;
    this.sortingProcessor = sortingProcessor;
    this.removeAboveMedianProcessor = removeAboveMedianProcessor;
  }

  public void process() {
    List<CarResult> carResults = carResultRepository.findAll();
    carResults = processCarResults(carResults);
    displayService.display(carResults);
  }

  protected List<CarResult> processCarResults(List<CarResult> carResults) {
    carResults = this.filterDuplicatesProcessor.processCarResults(carResults);
    carResults = this.sortingProcessor.processCarResults(carResults);
    carResults = this.removeAboveMedianProcessor.processCarResults(carResults);
    return carResults;
  }
}
