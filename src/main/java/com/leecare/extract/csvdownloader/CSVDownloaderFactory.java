/*
 * CSVDownloaderFactory.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

/**
 * This is used for a CSVDownloaderFactory.
 *
 * @author jjoy
 */
public class CSVDownloaderFactory {
  /**
   * Method to create corresponding csv downloader factory class
   *
   * @param aParameters parameters (not null)
   * @param aDataExtractionService data extraction service (not null)
   * @return corresponding downloader class.
   */
  public CSVDownloader createCSVDownloader(
      InputParameters aParameters, DataExtractionService aDataExtractionService) {
    if ((aParameters.getGridForm())) {
      return new GridFormFormCSVDownloader(aDataExtractionService);
    } else if (aParameters.getSubForm()) {
      return new SubFormFormCSVDownloader(aDataExtractionService);
    } else if (aParameters.getCustomGridForm()) {
      return new CustomGridFormFormCSVDownloader(aDataExtractionService);
    } else if (aParameters.getDownloadAttachments()) {
      return new AttachmentsDownloader(aDataExtractionService);
    } else if (aParameters.getRegularForm()) {
      return new RegularFormRangeCSVDownloader(aDataExtractionService);
    } else if (aParameters.getBedMovement()) {
      return new TotalBedMovementCSVDownloader(aDataExtractionService);
    } else if (aParameters.getPrescriptions()) {
      return new PrescriptionsCSVDownloader(aDataExtractionService);
    } else if (aParameters.getMedications()) {
      return new MedicationsCSVDownloader(aDataExtractionService);
    } else if (aParameters.getSddMedications()) {
      return new SDDMedicationsDownloader(aDataExtractionService);
    } else if (aParameters.getTasks()) {
      return new TasksCSVDownloader(aDataExtractionService);
    } else if (aParameters.getProgressNotes()) {
      return new ProgressNotesCSVDownloader(aDataExtractionService);
    } else if (aParameters.getAdverseReaction()) {
      return new AdverseReactionsCSVDownloader(aDataExtractionService);
    } else if (aParameters.getPdfExtract()) {
      return new ResidentPDFDownloader(aDataExtractionService);
    } else {
      throw new IllegalArgumentException("Invalid Option: Please provide correct parameters.");
    }
  }
}
