/*
 * MedicationsCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentRecordDetails;
import com.leecare.extract.service.DataExtractionService;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is used for a MedicationsCSVDownloader.
 *
 * @author jjoy
 */
public class MedicationsCSVDownloader extends CommonFormCSVDownloader {
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a MedicationsCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public MedicationsCSVDownloader(DataExtractionService aDataExtractionService) {
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
        "\"ToDate\":\"" + aParams.getToDate() + "\" , " +
        "\"excludeUnadmittedResidentsFlag\":\"" + aParams.getExcludeUnadmittedResidentsFlag() + "\" , " +
        "\"excludeArchivedResidentsFlag\":\"" + aParams.getExcludeArchivedResidentsFlag() + "\" , " +
        "\"excludeReservedResidentsFlag\":\"" + aParams.getExcludeReservedResidentsFlag() + "\"" +
        "}";
    Map<Integer, ResidentRecordDetails> residentDetailsMap =
        dataExtractionService.extractMedicationsDetails(aParams, jsonBody);
    TreeMap<Integer, ResidentRecordDetails> sortedResidentDetailsMap =
        new TreeMap<>(residentDetailsMap);
    super.prepareSummaryCSV(residentDetailsMap, "medications", aParams, null);
    super.downloadCSVForRange(
        aParams, "MEDICATIONS", aFieldCaptionMapping, "medications", sortedResidentDetailsMap);
  }
}
