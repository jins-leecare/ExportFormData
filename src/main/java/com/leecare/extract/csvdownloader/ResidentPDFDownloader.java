/*
 * ResidentPDFDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.PersonNoteDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;

/**
 * This is used for a ResidentPDFDownloader.
 *
 * @author jjoy
 */
public class ResidentPDFDownloader extends CommonCSVDownloader<PersonNoteDetails> {
  private static final Logger logger = LogManager.getLogger(ResidentPDFDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a ResidentPDFDownloader.
   *
   * @param aDataExtractionService dataExtractionService (not null)
   */
  public ResidentPDFDownloader(DataExtractionService aDataExtractionService) {
    this.dataExtractionService = aDataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) throws IOException, ParseException {
    String jsonBody = "{" + "\"path\":\"" + aParams.getFolderPath() + "\"" + "}";
    Response response = dataExtractionService.downloadResidentDocuments(aParams, jsonBody);
    logger.debug(response.getStatus());
  }
}
