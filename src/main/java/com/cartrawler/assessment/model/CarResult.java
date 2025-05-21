package com.cartrawler.assessment.model;

import java.util.List;
import java.util.Objects;

public class CarResult {
  private final String description;
  private final String supplierName;
  private final String sippCode;
  private final double rentalCost;
  private final FuelPolicy fuelPolicy;

  public enum FuelPolicy {
    FULLFULL,
    FULLEMPTY }

  public enum SippType {
    MINI,
    ECONOMY,
    COMPACT,
    OTHER }

  private static final List<String> CORPORATE_SUPPLIERS =
    List.of("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY");



  public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
    this.description = description;
    this.supplierName = supplierName;
    this.sippCode = sipp;
    this.rentalCost = cost;
    this.fuelPolicy = fuelPolicy;
  }

  public String getDescription() {
    return this.description;
  }

  public String getSupplierName() {
    return this.supplierName;
  }

  public String getSippCode() {
    return this.sippCode;
  }

  public double getRentalCost() {
    return this.rentalCost;
  }

  public FuelPolicy getFuelPolicy() {
    return this.fuelPolicy;
  }

  public boolean isCorporate() {
    return CORPORATE_SUPPLIERS.contains(this.supplierName);
  }

  public String getCorporate() {
    return isCorporate() ? "CORPORATE" : "NON_CORPORATE";
  }

  public SippType getSippType() {
    if (this.sippCode == null || this.sippCode.isEmpty()) return SippType.OTHER;
    String sippType = this.sippCode.substring(0, 1);
    return switch (sippType) {
      case "M" -> SippType.MINI;
      case "E" -> SippType.ECONOMY;
      case "C" -> SippType.COMPACT;
      default -> SippType.OTHER;
    };
  }

  @Override
  public String toString() {
    return
      getCorporate() + " : " +
      getSippType() + " : " +
      this.supplierName + " : " +
      this.description + " : " +
      this.sippCode + " : " +
      this.rentalCost + " : " +
      this.fuelPolicy;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CarResult that)) return false;
    return
      Double.compare(rentalCost, that.rentalCost) == 0 &&
      Objects.equals(description, that.description) &&
      Objects.equals(supplierName, that.supplierName) &&
      Objects.equals(sippCode, that.sippCode) &&
      fuelPolicy == that.fuelPolicy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, supplierName, sippCode, rentalCost, fuelPolicy);
  }
}
