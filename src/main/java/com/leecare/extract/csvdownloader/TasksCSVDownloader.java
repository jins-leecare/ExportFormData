/*
 * TasksCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a TasksCSVDownloader.
 *
 * @author jjoy
 */
public class TasksCSVDownloader extends CommonCSVDownloader<TasksRow> {
  private static final Logger logger = LogManager.getLogger(TasksCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a TasksCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public TasksCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters params) throws IOException, ParseException {
    String jsonBody = "{" +
        "\"FromDate\":\"" + params.getFromDate() + "\" , " +
        "\"ToDate\":\"" + params.getToDate() + "\"" +
        "}";
    List<TasksRow> tasksRowList = dataExtractionService.extractTaskDetails(params, jsonBody);
    Map<Integer, ResidentDetails> residentMap = new HashMap<>();
    if (Objects.nonNull(tasksRowList) && !tasksRowList.isEmpty()) {
      tasksRowList.forEach(
          tasksRow -> {
            if (tasksRow.getResidentID() != null
                && !residentMap.containsKey(tasksRow.getResidentID())) {
              residentMap.putIfAbsent(
                  tasksRow.getResidentID(),
                  dataExtractionService.extractResident(
                      params, tasksRow.getResidentID().toString()));
            }
          });
    }
    Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("tasks");
    super.downloadCSV(params, "TASKS", "tasks", tasksRowList, residentMap, fieldMapping);
  }
}
