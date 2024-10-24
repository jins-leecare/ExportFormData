/*
 * CustomGridFormFormCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a CustomGridFormFormCSVDownloader.
 *
 * @author jjoy
 */
public class CustomGridFormFormCSVDownloader extends CommonFormCSVDownloader {
  private static final Logger logger = LogManager.getLogger(CustomGridFormFormCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a CustomGridFormFormCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public CustomGridFormFormCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters params) {
    Map<String, String> fieldCaptionMapping =
        dataExtractionService.extractFieldCaptionMapping(params);
    if (Objects.isNull(params.getFormName())) {
      List<String> formNames = dataExtractionService.extractFormNames(params);
      if (Objects.isNull(formNames) || formNames.isEmpty()) {
        logger.error(
            "Data is not available for export. Please re-evaluate your parameters for downloading "
                + "custom grid forms");
        return;
      }
      formNames.forEach(
          form -> {
            retrieveDataAndDownloadCSV(params, fieldCaptionMapping, form);
          });
    } else {
      retrieveDataAndDownloadCSV(params, fieldCaptionMapping, params.getFormName());
    }
  }

  private void retrieveDataAndDownloadCSV(
      InputParameters aParams, Map<String, String> aFieldCaptionMapping, String aForm) {
    String jsonBody = "{" +
        "\"FormName\":\"" + aForm + "\" , " +
        "\"FromDate\":\"" + aParams.getFromDate() + "\" , " +
        "\"ToDate\":\"" + aParams.getToDate() + "\" , " +
        "\"CustomGridForm\": " + true +
        "}";
    Map<Integer, ResidentRecordDetails> residentDetailsMap =
        dataExtractionService.extractGridFormData(aParams, jsonBody);
    try {
      Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping(aForm);
      if (fieldMapping != null) {
        aFieldCaptionMapping.clear();
        aFieldCaptionMapping.putAll(fieldMapping);
      }
    } catch (Exception exception) {
      logger.error("Caught exception while extracting custom grid form {}", exception);
    }
    super.prepareSummaryCSV(residentDetailsMap, aForm, aParams, aFieldCaptionMapping);
    super.downloadCSVForRange(
        aParams, "CUSTOM-GRID-FORMS", aFieldCaptionMapping, aForm, residentDetailsMap, false);
  }
}
