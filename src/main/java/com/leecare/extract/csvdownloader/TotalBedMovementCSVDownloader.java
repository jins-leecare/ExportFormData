/*
 * TotalBedMovementCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is used for a TotalBedMovementCSVDownloader.
 *
 * @author jjoy
 */
public class TotalBedMovementCSVDownloader extends CommonCSVDownloader<TotalBedMovement> {
  private static final SimpleDateFormat DOB_FORMAT = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
  private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmm");
  private static final Logger logger = LogManager.getLogger(TotalBedMovementCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a TotalBedMovementCSVDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public TotalBedMovementCSVDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) throws IOException, ParseException {
    List<TotalBedMovement> bedMovements = dataExtractionService.extractTotalBedMovements(aParams);
    if (Objects.nonNull(bedMovements) || !bedMovements.isEmpty()) {
      Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("bedMovements");
      super.downloadBedMovementCSV(
          aParams, "BED-MOVEMENTS", "Total_BedMovements", bedMovements, fieldMapping);
    }
  }

  private static Date getFormattedDate(String aDateStringValue) throws ParseException {
    try {
      return DATE_FORMAT.parse(aDateStringValue);
    } catch (ParseException ex) {
      return DATE_FORMAT2.parse(aDateStringValue);
    }
  }
}
