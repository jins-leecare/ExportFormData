package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class ProgressNotesCSVDownloader extends CommonCSVDownloader<PersonNoteDetails> {
    DataExtractionService dataExtractionService;

    public ProgressNotesCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        String jsonBody = "{" +
                "\"FromDate\":\"" + params.getFromDate() + "\" , " +
                "\"ToDate\":\"" + params.getToDate() + "\" , " +
                "\"unReadOnly\":\"" + params.getUnReadOnly() + "\" , " +
                "\"excludeUnadmittedResidentsFlag\":\"" + params.getExcludeUnadmittedResidentsFlag() + "\"" +
                "}";
        List<PersonNoteDetails> personNoteDetails = dataExtractionService.extractProgressNotes(params, jsonBody);
        if (Objects.isNull(personNoteDetails) || personNoteDetails.isEmpty()) {
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading "
                    + "progress notes" );
            return;
        }
        Collections.sort(personNoteDetails, Comparator.comparingInt(PersonNoteDetails::getPersonId));
        Map<Integer, ResidentDetails> residentMap = new HashMap<>();
        personNoteDetails.forEach(personNote -> {
            if(!residentMap.containsKey(personNote.getPersonId())) {
                residentMap.putIfAbsent(personNote.getPersonId(), dataExtractionService.extractResident(params, String.valueOf(personNote.getPersonId())));
            }
        });
        Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("progressNotes");
        super.downloadCSV(params, "PROGRESS-NOTES", "progressNotes", personNoteDetails, residentMap, fieldMapping);
    }

    @Override
    public void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formName, InputParameters params) {

    }
}
