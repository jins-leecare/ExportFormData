package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomGridFormFormCSVDownloader extends CommonFormCSVDownloader {
    DataExtractionService dataExtractionService;
    public CustomGridFormFormCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) {
        Map<String, String> fieldCaptionMapping = dataExtractionService.extractFieldCaptionMapping(params);
        if (Objects.isNull(params.getFormName())) {
            List<String> formNames = dataExtractionService.extractFormNames(params);
            formNames.forEach(form -> {
                retrieveDataAndDownloadCSV(params, fieldCaptionMapping, form);
            });
        } else {
            retrieveDataAndDownloadCSV(params, fieldCaptionMapping, params.getFormName());
        }
    }

    private void retrieveDataAndDownloadCSV(InputParameters params, Map<String, String> fieldCaptionMapping, String form) {
        String jsonBody = "{" +
                "\"FormName\":\"" + form + "\" , " +
                "\"FromDate\":\"" + params.getFromDate() + "\" , " +
                "\"ToDate\":\"" + params.getToDate() + "\" , " +
                "\"CustomGridForm\": " + true +
                "}";
        Map<Integer, ResidentRecordDetails> residentDetailsMap = dataExtractionService.extractGridFormData(params, jsonBody);
        super.downloadCSVForRange(
                params, "CUSTOM-GRID-FORMS",
                fieldCaptionMapping,
                form,
                residentDetailsMap);
    }
}
