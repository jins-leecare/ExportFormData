package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;
import com.leecare.extract.model.PersonNoteDetails;
import com.leecare.extract.service.DataExtractionService;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class ResidentPDFDownloader extends CommonCSVDownloader<PersonNoteDetails> {
    DataExtractionService dataExtractionService;

    public ResidentPDFDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        String jsonBody = "{" +
                "\"path\":\"" + params.getFolderPath() + "\"" +
                "}";
        Response response = dataExtractionService.downloadResidentDocuments(params, jsonBody);
        System.out.println(response.getStatus());
    }

    @Override
    public void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formName, InputParameters params) {

    }
}
