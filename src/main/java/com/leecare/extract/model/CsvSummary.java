/*
 * CsvSummary.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.Map;

/**
 * This is used for a CsvSummary.
 *
 * @author jjoy
 */
public class CsvSummary {
  private int numberOfColumns;
  private int numberOfResidents;
  private Map<Integer, Integer> columnsWithValuesCount;
  /** @return the numberOfColumns */
  public int getNumberOfColumns() {
    return numberOfColumns;
  }
  /**
   * Sets the numberOfColumns.
   *
   * @param aNumberOfColumns the numberOfColumns to set
   */
  public void setNumberOfColumns(int aNumberOfColumns) {
    numberOfColumns = aNumberOfColumns;
  }
  /** @return the numberOfResidents */
  public int getNumberOfResidents() {
    return numberOfResidents;
  }
  /**
   * Sets the numberOfResidents.
   *
   * @param aNumberOfResidents the numberOfResidents to set
   */
  public void setNumberOfResidents(int aNumberOfResidents) {
    numberOfResidents = aNumberOfResidents;
  }
  /** @return the columnsWithValuesCount */
  public Map<Integer, Integer> getColumnsWithValuesCount() {
    return columnsWithValuesCount;
  }
  /**
   * Sets the columnsWithValuesCount.
   *
   * @param aColumnsWithValuesCount the columnsWithValuesCount to set
   */
  public void setColumnsWithValuesCount(Map<Integer, Integer> aColumnsWithValuesCount) {
    columnsWithValuesCount = aColumnsWithValuesCount;
  }
}
