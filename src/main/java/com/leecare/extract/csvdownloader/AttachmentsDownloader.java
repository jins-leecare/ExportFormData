package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading "
                    + "attachments");
            return;
        }
        saveFileAttachmentsToFolder(params, "ATTACHMENTS", fileAttachments);
    }

    @Override
    public void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formNam, InputParameters params) {

    }
}
