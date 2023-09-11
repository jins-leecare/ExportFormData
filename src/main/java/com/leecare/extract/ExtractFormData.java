package com.leecare.extract;

import com.leecare.extract.csvdownloader.CSVDownloader;
import com.leecare.extract.csvdownloader.CSVDownloaderFactory;
import com.leecare.extract.model.InputParameters;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import static com.leecare.extract.utils.ExtractionUtils.parseCommandLineArgs;
import static com.leecare.extract.utils.ExtractionUtils.readConfigFile;

public class ExtractFormData {

    public static void main(String[] args) throws IOException, ParseException {

        if (args.length < 2) {
            System.out.println("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        InputParameters params = parseCommandLineArgs(args);

        if (Objects.isNull(params.getFacilityId()) || Objects.isNull(params.getConfigFile().equals(""))) {
            System.out.println("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        params.setConfigProperties(readConfigFile(params.getConfigFile()));

        CSVDownloaderFactory factory = new CSVDownloaderFactory();
        DataExtractionService dataExtractionService = new DataExtractionService();

        CSVDownloader downloader = factory.createCSVDownloader(params, dataExtractionService);
        downloader.downloadCSV(params);
        System.out.println("CSV files downloaded successfully");
    }
}
