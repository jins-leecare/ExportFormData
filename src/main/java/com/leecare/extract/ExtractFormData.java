/*
 * ExtractFormData.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract;

import com.leecare.extract.csvdownloader.CSVDownloader;
import com.leecare.extract.csvdownloader.CSVDownloaderFactory;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import static com.leecare.extract.utils.ExtractionUtils.parseCommandLineArgs;
import static com.leecare.extract.utils.ExtractionUtils.readConfigFile;

/**
 * This is used for a ExtractFormData.
 *
 * @author jjoy
 */
public class ExtractFormData {
    private static final Logger logger = LogManager.getLogger(ExtractFormData.class);

    /**
     * Main method which accept the command line arguments 
     * and process the request accordingly.
     * @param aArgs arguments
     * @throws IOException IO exception
     * @throws ParseException parseException
     */
    public static void main(String[] aArgs) throws IOException, ParseException {

        if (aArgs.length < 2) {
            logger.error("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true "
                + "facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        InputParameters params = parseCommandLineArgs(aArgs);
        if (Objects.isNull(params.getFacilityId()) || Objects.isNull(params.getConfigFile().equals(""))) {
            logger.error("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true "
                + "facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        params.setConfigProperties(readConfigFile(params.getConfigFile()));

        CSVDownloaderFactory factory = new CSVDownloaderFactory();
        DataExtractionService dataExtractionService = new DataExtractionService();

        CSVDownloader downloader = factory.createCSVDownloader(params, dataExtractionService);
        downloader.downloadCSV(params);
        logger.debug("CSV files downloaded successfully");
    }
}
