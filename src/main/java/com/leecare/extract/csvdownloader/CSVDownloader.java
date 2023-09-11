package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.InputParameters;

import java.io.IOException;
import java.text.ParseException;

public interface CSVDownloader {
    void downloadCSV(InputParameters params) throws IOException, ParseException;
}
