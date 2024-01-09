package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface CSVDownloader {
    /**
     * Method to download the csv.
     * 
     * @param aParams parameters (not null)
     * @throws IOException
     * @throws ParseException
     */
    void downloadCSV(InputParameters aParams) throws IOException, ParseException;

    /**
     * Method to prepare summary.
     * 
     * @param aResidentDetailsMap resident details map (can be null)
     * @param aFormName form name (not null)
     * @param aParams parameters (not null)
     */
    default void prepareSummaryCSV(Map<Integer, ?> aResidentDetailsMap, String aFormName, InputParameters aParams) {
    }

}
