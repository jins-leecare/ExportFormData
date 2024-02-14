/*
 * SubFormFormCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a SubFormFormCSVDownloader.
 *
 * @author jjoy
 */
public class SubFormFormCSVDownloader extends CommonFormCSVDownloader {
  private static final Logger logger = LogManager.getLogger(SubFormFormCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a SubFormFormCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public SubFormFormCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) {
    Map<String, String> fieldCaptionMapping =
        dataExtractionService.extractFieldCaptionMapping(aParams);
    if (Objects.isNull(aParams.getFormName())) {
      List<String> formNames = dataExtractionService.extractFormNames(aParams);
      if (Objects.isNull(formNames) || formNames.isEmpty()) {
        logger.error("Data is not available for export. Please re-evaluate your parameters.");
        return;
      }
      formNames.forEach(
          form -> {
            retrieveDataAndDownloadCSV(aParams, fieldCaptionMapping, form);
          });
    } else {
      retrieveDataAndDownloadCSV(aParams, fieldCaptionMapping, aParams.getFormName());
    }
  }

  private void retrieveDataAndDownloadCSV(
      InputParameters aParams, Map<String, String> aFieldCaptionMapping, String aForm) {
    String jsonBody = "{" +
        "\"FormName\":\"" + aForm + "\" , " +
        "\"FromDate\":\"" + aParams.getFromDate() + "\" , " +
        "\"ToDate\":\"" + aParams.getToDate() + "\"" +
        "}";
    Map<Integer, ResidentRecordDetails> residentDetailsMap =
        dataExtractionService.extractGridFormData(aParams, jsonBody);
    super.prepareSummaryCSV(residentDetailsMap, aForm, aParams, null);
    super.downloadCSVForRange(
        aParams, "SUB-FORMS", aFieldCaptionMapping, aForm, residentDetailsMap);
  }
}
