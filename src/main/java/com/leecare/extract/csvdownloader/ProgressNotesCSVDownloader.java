/*
 * ProgressNotesCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * This is used for a ProgressNotesCSVDownloader.
 *
 * @author jjoy
 */
public class ProgressNotesCSVDownloader extends CommonCSVDownloader<PersonNoteDetails> {
  private static final Logger logger = LogManager.getLogger(ProgressNotesCSVDownloader.class);

  private DataExtractionService dataExtractionService;

  /**
   * Constructs a ProgressNotesCSVDownloader.
   *
   * @param dataExtractionService
   */
  public ProgressNotesCSVDownloader(DataExtractionService dataExtractionService) {
    this.dataExtractionService = dataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) throws IOException, ParseException {
    String jsonBody = "{" +
        "\"FromDate\":\"" + aParams.getFromDate() + "\" , " +
        "\"ToDate\":\"" + aParams.getToDate() + "\" , " +
        "\"unReadOnly\":\"" + aParams.getUnReadOnly() + "\" , " +
        "\"excludeUnadmittedResidentsFlag\":\"" + aParams.getExcludeUnadmittedResidentsFlag() + "\"" +
        "}";
    List<PersonNoteDetails> personNoteDetails =
        dataExtractionService.extractProgressNotes(aParams, jsonBody);
    if (Objects.isNull(personNoteDetails) || personNoteDetails.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + "progress notes");
      return;
    }
    Collections.sort(personNoteDetails, Comparator.comparingInt(PersonNoteDetails::getPersonId));
    Map<Integer, ResidentDetails> residentMap = new HashMap<>();
    personNoteDetails.forEach(
        personNote -> {
          if (!residentMap.containsKey(personNote.getPersonId())) {
            residentMap.putIfAbsent(
                personNote.getPersonId(),
                dataExtractionService.extractResident(
                    aParams, String.valueOf(personNote.getPersonId())));
          }
        });
    Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("progressNotes");
    Map<Integer, List<PersonNoteDetails>> personNoteDetailsHashMap = createHashMap(personNoteDetails);
    super.prepareSummaryCSV(personNoteDetailsHashMap, "progressNotes", aParams, fieldMapping);
    super.downloadCSV(
        aParams, "PROGRESS-NOTES", "progressNotes", personNoteDetails, residentMap, fieldMapping);
  }

  private static Map<Integer, List<PersonNoteDetails>> createHashMap(List<PersonNoteDetails> aPersonNoteDetailsList) {
    Map<Integer, List<PersonNoteDetails>> personNoteDetailsMap = new HashMap<>();

    for (PersonNoteDetails personNoteDetails : aPersonNoteDetailsList) {
      if (personNoteDetails.getPersonDetails() != null) {
        int personId = personNoteDetails.getPersonDetails().getPersonId();
        List<PersonNoteDetails> personNoteList;
        if (personNoteDetailsMap.containsKey(personId)) {
          personNoteList = personNoteDetailsMap.get(personId);
        } else {
          personNoteList = new ArrayList<>();
        }
        personNoteList.add(personNoteDetails);
        personNoteDetailsMap.put(personId, personNoteList);
      }
    }
    return personNoteDetailsMap;
  }
}
