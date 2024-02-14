/*
 * RegularFormFormCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.ResidentDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a RegularFormFormCSVDownloader.
 *
 * @author jjoy
 */
public class RegularFormFormCSVDownloader extends CommonFormCSVDownloader {
  private static final Logger logger = LogManager.getLogger(RegularFormFormCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a RegularFormFormCSVDownloader.
   *
   * @param aDataExtractionService aDataExtractionService (not null)
   */
  public RegularFormFormCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) {
    Map<String, String> fieldCaptionMapping =
        dataExtractionService.extractFieldCaptionMapping(aParams);
    if (Objects.isNull(aParams.getFormName())) {
      List<String> formNames = dataExtractionService.extractFormNames(aParams);
      if (Objects.nonNull(formNames)) {
        formNames.forEach(
            form -> {
              retrieveDataAndDownloadCSV(aParams, fieldCaptionMapping, form);
            });
      } else {
        logger.error("Please check your application is working correctly");
      }
    } else {
      retrieveDataAndDownloadCSV(aParams, fieldCaptionMapping, aParams.getFormName());
    }
  }

  private void retrieveDataAndDownloadCSV(
      InputParameters aParams, Map<String, String> aFieldCaptionMapping, String aForm) {
    String jsonBody = "{" + "\"FormName\":\"" + aForm + "\"" + "}";
    Map<Integer, ResidentDetails> residentDetailsMap =
        dataExtractionService.extractFormData(aParams, jsonBody);
    super.prepareSummaryCSV(residentDetailsMap, aForm, aParams, null);
    super.downloadCSV(aParams, "REGULAR-FORMS", aFieldCaptionMapping, aForm, residentDetailsMap);
  }
}
