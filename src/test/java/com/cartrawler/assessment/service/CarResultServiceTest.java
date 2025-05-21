package com.cartrawler.assessment.service;

import com.cartrawler.assessment.model.CarResult;
import com.cartrawler.assessment.processor.FilterDuplicatesProcessor;
import com.cartrawler.assessment.processor.RemoveAboveMedianProcessor;
import com.cartrawler.assessment.processor.SortingProcessor;
import com.cartrawler.assessment.repository.CarResultRepository;
import com.cartrawler.assessment.config.ProcessingOptions;
import com.cartrawler.assessment.service.strategy.FilterDuplicatesStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.cartrawler.assessment.model.CarResult.FuelPolicy.FULLEMPTY;
import static com.cartrawler.assessment.model.CarResult.FuelPolicy.FULLFULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CarResultServiceTest {

  private static final String TEST_OUTPUT_FILE = "test-output.txt";

  CarResultRepository carResultRepository = Mockito.mock(CarResultRepository.class);

  DisplayService displayService;

  CarResultService sut;

  @ParameterizedTest
  @MethodSource("processingOptions")
  void process(FilterDuplicatesStrategy.Name strategy, boolean removeAboveMedian, String expectedOutput) throws IOException, URISyntaxException {
    // Given
    when(carResultRepository.findAll()).thenReturn(createCarResults());
    final ByteArrayOutputStream displayedContent = new ByteArrayOutputStream();
    this.displayService = new DisplayService(new PrintStream(displayedContent));
    ProcessingOptions processingOptions = new ProcessingOptions(strategy, removeAboveMedian);
    sut = new CarResultService(
      carResultRepository,
      displayService,
      new FilterDuplicatesProcessor(processingOptions),
      new SortingProcessor(),
      new RemoveAboveMedianProcessor(processingOptions));

    // When
    sut.process();

    // Then
    String expectedContent = readFileContent(expectedOutput);
    String actualContent = displayedContent.toString();
    assertEquals(expectedContent, actualContent);
  }

  static Object[][] processingOptions() {
    return new Object[][] {
      new Object[] { FilterDuplicatesStrategy.Name.MIN, false, "test-MIN-false-expected.txt" },
      new Object[] { FilterDuplicatesStrategy.Name.MAX, false, "test-MAX-false-expected.txt" },
      new Object[] { FilterDuplicatesStrategy.Name.AVERAGE, false, "test-AVERAGE-false-expected.txt" },
      new Object[] { FilterDuplicatesStrategy.Name.MIN, true, "test-MIN-true-expected.txt" },
      new Object[] { FilterDuplicatesStrategy.Name.MAX, true, "test-MAX-true-expected.txt" },
      new Object[] { FilterDuplicatesStrategy.Name.AVERAGE, true, "test-AVERAGE-true-expected.txt" },
    };
  }

  String readFileContent(String fileName) throws IOException, URISyntaxException {
    return Files.readString(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
  }

  List<CarResult> createCarResults() {
    return List.of(
      new CarResult("NONCORP IVMR FF 300", "DELPASO", "IVMR", 300d, FULLFULL),
      new CarResult("NONCORP CDMR FF 140", "DELPASO", "CWMR", 140d, FULLFULL),
      new CarResult("NONCORP CDMR FF DUPLICATE", "CENTAURO", "CDMR", 70d, FULLFULL),
      new CarResult("NONCORP CDMR FF DUPLICATE", "CENTAURO", "CDMR", 75d, FULLFULL),
      new CarResult("NONCORP EDMR FF DUPLICATE", "GOLDCAR", "EDMR", 1d, FULLFULL),
      new CarResult("NONCORP EDMR FF DUPLICATE", "GOLDCAR", "EDMR", 4d, FULLFULL),
      new CarResult("NONCORP EDMR FF DUPLICATE", "GOLDCAR", "EDMR", 10d, FULLFULL),
      new CarResult("NONCORP MDMR FE DUPLICATE", "NIZA", "MDMR", 10d, FULLEMPTY),
      new CarResult("NONCORP MDMR FE DUPLICATE", "NIZA", "MDMR", 20d, FULLEMPTY),
      new CarResult("NONCORP MDMR FE DUPLICATE", "NIZA", "MDMR", 30d, FULLEMPTY),
      new CarResult("NONCORP MCMR FE", "MARBESOL", "MCMR", 32d, FULLEMPTY),
      new CarResult("CORP IVMR FE DUPLICATE 1", "AVIS", "IVMR", 1d, FULLEMPTY),
      new CarResult("CORP IVMR FE DUPLICATE 1", "AVIS", "IVMR", 1d, FULLEMPTY),
      new CarResult("CORP IVMR FE DUPLICATE 1", "AVIS", "IVMR", 1d, FULLEMPTY),
      new CarResult("CORP MCMR FE", "SIXT", "MCMR", 32d, FULLEMPTY),
      new CarResult("CORP MUMR FE DUPLICATE", "HERTZ", "MUMR", 15d, FULLEMPTY),
      new CarResult("CORP MUMR FE DUPLICATE", "HERTZ", "MUMR", 25d, FULLEMPTY),
      new CarResult("CORP MUMR FE DUPLICATE", "HERTZ", "MUMR", 35d, FULLEMPTY),
      new CarResult("CORP IVMR FE 80", "FIREFLY", "IVMR", 80d, FULLEMPTY),
      new CarResult("CORP IVMR FE 120", "FIREFLY", "IVMR", 120d, FULLEMPTY)
    );
  }
}

