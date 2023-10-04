package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.AdverseReactionDetails;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.PersonNoteDetails;
import com.leecare.extract.model.Resident;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class AdverseReactionsCSVDownloader extends CommonCSVDownloader<AdverseReactionDetails> {
    DataExtractionService dataExtractionService;

    public AdverseReactionsCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        String jsonBody = "{}";
        List<AdverseReactionDetails> adverseReactionDetails = dataExtractionService.extractAdverseReactions(params, jsonBody);
        if (Objects.isNull(adverseReactionDetails) || adverseReactionDetails.isEmpty()) {
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading "
                    + "adverse reactions" );
            return;
        }
        Collections.sort(adverseReactionDetails, Comparator.comparingInt(AdverseReactionDetails::getResidentId));
        Map<Integer, Resident> residentMap = new HashMap<>();
        adverseReactionDetails.forEach(adverseReaction -> {
            if(!residentMap.containsKey(adverseReaction.getResidentId())) {
                residentMap.putIfAbsent(adverseReaction.getResidentId(), dataExtractionService.extractResident(params, String.valueOf(adverseReaction.getResidentId())));
            }
        });
        super.downloadCSV(params, "ADVERSE-REACTIONS", "adverseReactions", adverseReactionDetails, residentMap);
    }
}
