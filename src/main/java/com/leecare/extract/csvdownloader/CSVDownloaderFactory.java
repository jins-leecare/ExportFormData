package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

public class CSVDownloaderFactory {
    public CSVDownloader createCSVDownloader(InputParameters parameters, DataExtractionService dataExtractionService) {
        if ((parameters.getGridForm())) {
            return new GridFormFormCSVDownloader(dataExtractionService);
        } else if (parameters.getSubForm()) {
            return new SubFormFormCSVDownloader(dataExtractionService);
        } else if (parameters.getCustomGridForm()) {
            return new CustomGridFormFormCSVDownloader(dataExtractionService);
        } else if (parameters.getDownloadAttachments()) {
            return new AttachmentsDownloader(dataExtractionService);
        } else if (parameters.getRegularForm()) {
            return new RegularFormFormCSVDownloader(dataExtractionService);
        } else if (parameters.getBedMovement()) {
            return new BedMovementCSVDownloader(dataExtractionService);
        } else if (parameters.getPrescriptions()) {
            return new PrescriptionsCSVDownloader(dataExtractionService);
        } else if (parameters.getMedications()) {
            return new MedicationsCSVDownloader(dataExtractionService);
        } else if (parameters.getTasks()) {
            return new TasksCSVDownloader(dataExtractionService);
        } else if (parameters.getProgressNotes()) {
            return new ProgressNotesCSVDownloader(dataExtractionService);
        } else if (parameters.getAdverseReaction()) {
            return new AdverseReactionsCSVDownloader(dataExtractionService);
        } else if (parameters.getPdfExtract()) {
            return new ResidentPDFDownloader(dataExtractionService);
        } else {
            throw new IllegalArgumentException("Invalid Option: Please provide correct parameters.");
        }
    }
}
