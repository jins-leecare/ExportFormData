/*
 * PrescriptionsCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;

import java.util.*;

/**
 * This is used for a PrescriptionsCSVDownloader.
 *
 * @author jjoy
 */
public class PrescriptionsCSVDownloader extends CommonFormCSVDownloader {
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a PrescriptionsCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public PrescriptionsCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) {
    retrieveDataAndDownloadCSV(aParams, new HashMap<>());
  }

  private void retrieveDataAndDownloadCSV(
      InputParameters aParams, Map<String, String> aFieldCaptionMapping) {
    String jsonBody = "{" +
        "\"FromDate\":\"" + aParams.getFromDate() + "\" , " +
        "\"ToDate\":\"" + aParams.getToDate() + "\"" +
        "}";
    Map<Integer, ResidentRecordDetails> residentDetailsMap =
        dataExtractionService.extractPrescriptionDetails(aParams, jsonBody);
    TreeMap<Integer, ResidentRecordDetails> sortedResidentDetailsMap =
        new TreeMap<>(residentDetailsMap);
    super.prepareSummaryCSV(residentDetailsMap, "prescriptions", aParams, null);
    super.downloadCSVForRange(
        aParams, "PRESCRIPTIONS", aFieldCaptionMapping, "Prescriptions", sortedResidentDetailsMap);
  }
}
