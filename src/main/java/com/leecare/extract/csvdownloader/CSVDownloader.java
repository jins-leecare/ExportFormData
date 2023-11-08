package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface CSVDownloader {
    void downloadCSV(InputParameters params) throws IOException, ParseException;

    void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formName, InputParameters params);

}
