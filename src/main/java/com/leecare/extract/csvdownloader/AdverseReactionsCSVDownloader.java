/*
 * AdverseReactionsCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.handler.CsvToUiFieldMappingLoader;
import com.leecare.extract.model.AdverseReactionDetails;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.PersonNoteDetails;
import com.leecare.extract.model.ResidentDetails;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * This is used for a AdverseReactionsCSVDownloader.
 *
 * @author jjoy
 */
public class AdverseReactionsCSVDownloader extends CommonCSVDownloader<AdverseReactionDetails> {
  private static final Logger logger = LogManager.getLogger(AdverseReactionsCSVDownloader.class);
  private DataExtractionService dataExtractionService;

  /**
   * Constructs a AdverseReactionsCSVDownloader.
   *
   * @param dataExtractionService data extraction service
   */
  public AdverseReactionsCSVDownloader(DataExtractionService dataExtractionService) {
    this.dataExtractionService = dataExtractionService;
  }

  @Override
  public void downloadCSV(InputParameters aParams) throws IOException, ParseException {
    String jsonBody = "{}";
    List<AdverseReactionDetails> adverseReactionDetails =
        dataExtractionService.extractAdverseReactions(aParams, jsonBody);
    if (Objects.isNull(adverseReactionDetails) || adverseReactionDetails.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + "adverse reactions");
      return;
    }
    Collections.sort(
        adverseReactionDetails, Comparator.comparingInt(AdverseReactionDetails::getResidentId));
    Map<Integer, ResidentDetails> residentMap = new HashMap<>();
    adverseReactionDetails.forEach(
        adverseReaction -> {
          if (!residentMap.containsKey(adverseReaction.getResidentId())) {
            residentMap.putIfAbsent(
                adverseReaction.getResidentId(),
                dataExtractionService.extractResident(
                    aParams, String.valueOf(adverseReaction.getResidentId())));
          }
        });
    Map<String, String> fieldMapping = CsvToUiFieldMappingLoader.loadMapping("adverseReactions");
    Map<Integer, List<AdverseReactionDetails>> adverseReactionDetailsMap = createHashMap(adverseReactionDetails);
    super.prepareSummaryCSV(adverseReactionDetailsMap, "Adverse Reactions", aParams, fieldMapping);
    super.downloadCSV(
        aParams,
        "ADVERSE-REACTIONS",
        "adverseReactions",
        adverseReactionDetails,
        residentMap,
        fieldMapping);
  }

  private static Map<Integer, List<AdverseReactionDetails>> createHashMap(List<AdverseReactionDetails> aAdverseReactionDetails) {
    Map<Integer, List<AdverseReactionDetails>> adverseReactionDetailsMap = new HashMap<>();

    for (AdverseReactionDetails adverseReactionDetails : aAdverseReactionDetails) {
        int personId = adverseReactionDetails.getResidentId();
        List<AdverseReactionDetails> adverseReactionDetailsList;
        if (adverseReactionDetailsMap.containsKey(personId)) {
          adverseReactionDetailsList = adverseReactionDetailsMap.get(personId);
        } else {
          adverseReactionDetailsList = new ArrayList<>();
        }
      adverseReactionDetailsList.add(adverseReactionDetails);
      adverseReactionDetailsMap.put(personId, adverseReactionDetailsList);
    }
    return adverseReactionDetailsMap;
  }
}
