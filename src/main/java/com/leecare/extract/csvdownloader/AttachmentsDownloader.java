package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.util.List;

public class AttachmentsDownloader extends CommonFormCSVDownloader {
    DataExtractionService dataExtractionService;
    public AttachmentsDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException {
        List<FileAttachment> fileAttachments = dataExtractionService.extractFileAttachments(params);
        saveFileAttachmentsToFolder(params, "ATTACHMENTS", fileAttachments);
    }
}
