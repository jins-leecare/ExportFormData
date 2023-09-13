package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TasksCSVDownloader extends CommonCSVDownloader<TasksRow> {
    DataExtractionService dataExtractionService;

    public TasksCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        String jsonBody = "{" +
                "\"FromDate\":\"" + params.getFromDate() + "\" , " +
                "\"ToDate\":\"" + params.getToDate() + "\"" +
                "}";
        List<TasksRow> tasksRowList = dataExtractionService.extractTaskDetails(params, jsonBody);
        super.downloadCSV(params, "TASKS", "tasks", tasksRowList);
    }
}
