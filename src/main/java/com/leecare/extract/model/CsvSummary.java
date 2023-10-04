package com.leecare.extract.model;

import java.util.HashMap;
import java.util.Map;

public class CsvSummary {
    private int numberOfColumns;
    private int numberOfResidents;
    private Map<Integer, Integer> columnsWithValuesCount;

    // Constructors, getters, and setters for the fields

    // Constructor
    public CsvSummary() {
        columnsWithValuesCount = new HashMap<>();
    }

    // Getter and Setter methods
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(int numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    public Map<Integer, Integer> getColumnsWithValuesCount() {
        return columnsWithValuesCount;
    }

    public void setColumnsWithValuesCount(Map<Integer, Integer> columnsWithValuesCount) {
        this.columnsWithValuesCount = columnsWithValuesCount;
    }
}
