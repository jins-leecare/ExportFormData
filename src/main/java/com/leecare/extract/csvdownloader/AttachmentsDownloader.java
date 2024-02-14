/*
 * AttachmentsDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a AttachmentsDownloader.
 *
 * @author jjoy
 */
public class AttachmentsDownloader extends CommonFormCSVDownloader {
  private static final Logger logger = LogManager.getLogger(AttachmentsDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a AttachmentsDownloader.
   *
   * @param dataExtractionService
   */
  public AttachmentsDownloader(DataExtractionService dataExtractionService) {
    this.dataExtractionService = dataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters params) throws IOException {
    List<FileAttachment> fileAttachments = dataExtractionService.extractFileAttachments(params);
    if (Objects.isNull(fileAttachments) || fileAttachments.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + "attachments");
      return;
    }
    saveFileAttachmentsToFolder(params, "ATTACHMENTS", fileAttachments);
  }
}
