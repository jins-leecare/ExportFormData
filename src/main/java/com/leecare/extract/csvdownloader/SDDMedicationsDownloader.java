/*
 * SDDMedicationsDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.MedicationData;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * This is used for a SDDMedicationsDownloader.
 *
 * @author jjoy
 */
public class SDDMedicationsDownloader extends CommonCSVDownloader<MedicationData> {
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a SDDMedicationsDownloader.
   *
   * @param aDataExtractionService
   */
  public SDDMedicationsDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) throws IOException, ParseException {
    List<MedicationData> medicationDataList = dataExtractionService.extractSDDMedications(aParams);
    if (Objects.nonNull(medicationDataList) || !medicationDataList.isEmpty()) {
      super.downloadCSVFromList(
          aParams, "SDD-MEDICATIONS", "SDD_medication_master", medicationDataList, null);
    }
  }
}
