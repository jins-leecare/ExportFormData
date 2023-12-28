package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.MedicationData;
import com.leecare.extract.model.TotalBedMovement;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SDDMedicationsDownloader extends CommonCSVDownloader<MedicationData> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmm");

    DataExtractionService dataExtractionService;

    public SDDMedicationsDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        List<MedicationData> medicationDataList = dataExtractionService.extractSDDMedications(params);
        if (Objects.nonNull(medicationDataList) || !medicationDataList.isEmpty()) {
            Map<String, String> fieldMapping = new HashMap<>();
                    //CsvToUiFieldMappingLoader.loadMapping("bedMovements");
            super.downloadBedMovementCSV(params,
                    "SDD-MEDICATIONS",
                    "SDD_medication_master",
                    medicationDataList, null);
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
