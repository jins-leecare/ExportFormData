package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegularFormFormCSVDownloader extends CommonFormCSVDownloader {
    DataExtractionService dataExtractionService;

    public RegularFormFormCSVDownloader(DataExtractionService dataExtractionService) {
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
                "\"FormName\":\"" + form + "\"" +
                "}";
        Map<Integer, ResidentDetails> residentDetailsMap = dataExtractionService.extractFormData(params, jsonBody);
        super.downloadCSV(params,
                "REGULAR-FORMS",
                fieldCaptionMapping,
                form,
                residentDetailsMap);
    }

}