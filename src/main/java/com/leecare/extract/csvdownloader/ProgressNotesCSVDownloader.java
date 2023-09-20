package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.PersonNoteDetails;
import com.leecare.extract.model.Resident;
import com.leecare.extract.model.TasksRow;
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
                "\"ToDate\":\"" + params.getToDate() + "\"" +
                "}";
        List<PersonNoteDetails> personNoteDetails = dataExtractionService.extractProgressNotes(params, jsonBody);
        Collections.sort(personNoteDetails, Comparator.comparingInt(PersonNoteDetails::getPersonId));
        Map<Integer, Resident> residentMap = new HashMap<>();
        personNoteDetails.forEach(personNote -> {
            if(!residentMap.containsKey(personNote.getPersonId())) {
                residentMap.putIfAbsent(personNote.getPersonId(), dataExtractionService.extractResident(params, String.valueOf(personNote.getPersonId())));
            }
        });
        super.downloadCSV(params, "PROGRESS-NOTES", "progressNotes", personNoteDetails, residentMap);
    }
}
