package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TotalBedMovementCSVDownloader extends CommonCSVDownloader<TotalBedMovement> {
    private static final SimpleDateFormat DOB_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmm");

    DataExtractionService dataExtractionService;

    public TotalBedMovementCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        List<TotalBedMovement> bedMovements = dataExtractionService.extractTotalBedMovements(params);
        if (Objects.nonNull(bedMovements) || !bedMovements.isEmpty()) {
            Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("bedMovements");
            super.downloadBedMovementCSV(params,
                    "BED-MOVEMENTS",
                    "Total_BedMovements",
                    bedMovements, fieldMapping);
        }
    }

    private static Date getFormattedDate(String dateStringValue) throws ParseException {
        try {
            return DATE_FORMAT.parse(dateStringValue);
        } catch (ParseException ex) {
            return DATE_FORMAT2.parse(dateStringValue);
        }
    }
}
