package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AttachmentsDownloader extends CommonFormCSVDownloader {
    DataExtractionService dataExtractionService;

    public AttachmentsDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException {
        List<FileAttachment> fileAttachments = dataExtractionService.extractFileAttachments(params);
        if (Objects.isNull(fileAttachments) || fileAttachments.isEmpty()) {
            throw new IllegalStateException("Data is not available for export. Please re-evaluate your parameters.");
        }
        saveFileAttachmentsToFolder(params, "ATTACHMENTS", fileAttachments);
    }
}
