package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrescriptionsCSVDownloader extends CommonFormCSVDownloader {

    DataExtractionService dataExtractionService;

    public PrescriptionsCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) {
        retrieveDataAndDownloadCSV(params, new HashMap<>());
    }

    private void retrieveDataAndDownloadCSV(InputParameters params, Map<String, String> fieldCaptionMapping) {
        String jsonBody = "{" +
                "\"FromDate\":\"" + params.getFromDate() + "\" , " +
                "\"ToDate\":\"" + params.getToDate() + "\"" +
                "}";
        Map<Integer, ResidentRecordDetails> residentDetailsMap = dataExtractionService.extractPrescriptionDetails(params, jsonBody);
        super.downloadCSVForRange(params,
                "PRESCRIPTIONS",
                fieldCaptionMapping,
                "Prescriptions",
                residentDetailsMap);
    }
}
