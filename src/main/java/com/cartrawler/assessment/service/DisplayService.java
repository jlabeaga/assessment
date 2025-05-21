package com.cartrawler.assessment.service;

import com.cartrawler.assessment.model.CarResult;

import java.io.PrintStream;
import java.util.List;

public class DisplayService {

  private final PrintStream printStream;

  public DisplayService() {
    this(System.out);
  }

  public DisplayService(PrintStream printStream) {
    this.printStream = printStream;
  }

  public void display(List<CarResult> carResults) {
    if (carResults == null || carResults.isEmpty()) {
      return;
    }
    carResults.forEach(carResult -> printStream.println(formatMessage(carResult)));
  }

  private String formatMessage(CarResult carResult) {
    return String.format(
      "| %-35s | %-12s | %-5s | %6s | %-10s |",
      carResult.getDescription(),
      carResult.getSupplierName(),
      carResult.getSippCode(),
      String.format("%.2f", carResult.getRentalCost()),
      carResult.getFuelPolicy());
  }
}
