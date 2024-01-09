/*
 * ExtractionUtils.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.utils;

import com.leecare.extract.csvdownloader.ResidentPDFDownloader;
import com.leecare.extract.model.ConfigProperties;
import com.leecare.extract.model.InputParameters;
import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * This is used for a ExtractionUtils.
 *
 * @author jjoy
 */
public class ExtractionUtils {
  private static final org.apache.logging.log4j.Logger logger =
      LogManager.getLogger(ExtractionUtils.class);

  /**
   * Method to parse command line arguments.
   *
   * @param args arguments (not null)
   * @return input parameters object (not null)
   */
  public static InputParameters parseCommandLineArgs(String[] args) {
    InputParameters params = new InputParameters();
    for (String arg : args) {
      String[] keyValue = arg.split("=");
      if (keyValue.length == 2) {
        String key = keyValue[0];
        String value = keyValue[1];

        if ("-formName".equals(key)) {
          params.setFormName(value);
        } else if ("-facilityId".equals(key)) {
          params.setFacilityId(value);
        } else if ("-configFile".equals(key)) {
          params.setConfigFile(value);
        } else if ("-regularForm".equals(key)) {
          params.setRegularForm(Boolean.valueOf(value));
        } else if ("-subForm".equals(key)) {
          params.setSubForm(Boolean.valueOf(value));
        } else if ("-gridForm".equals(key)) {
          params.setGridForm(Boolean.valueOf(value));
        } else if ("-customGridForm".equals(key)) {
          params.setCustomGridForm(Boolean.valueOf(value));
        } else if ("-downloadAttachments".equals(key)) {
          params.setDownloadAttachments(Boolean.valueOf(value));
        } else if ("-bedMovement".equals(key)) {
          params.setBedMovement(Boolean.valueOf(value));
        } else if ("-prescriptions".equals(key)) {
          params.setPrescriptions(Boolean.valueOf(value));
        } else if ("-medications".equals(key)) {
          params.setMedications(Boolean.valueOf(value));
        } else if ("-fromDate".equals(key)) {
          params.setFromDate(value);
        } else if ("-toDate".equals(key)) {
          params.setToDate(value);
        } else if ("-folderPath".equals(key)) {
          params.setFolderPath(value);
        } else if ("-tasks".equals(key)) {
          params.setTasks(Boolean.valueOf(value));
        } else if ("-progressNotes".equals(key)) {
          params.setProgressNotes(Boolean.valueOf(value));
        } else if ("-adverseReactions".equals(key)) {
          params.setAdverseReaction(Boolean.valueOf(value));
        } else if ("-SDDMedications".equals(key)) {
          params.setSddMedications(Boolean.valueOf(value));
        } else if ("-pdfExtract".equals(key)) {
          params.setPdfExtract(Boolean.valueOf(value));
        } else if ("-excludeUnadmittedResidentsFlag".equals(key)) {
          params.setExcludeUnadmittedResidentsFlag(Boolean.valueOf(value));
        } else if ("-excludeArchivedResidentsFlag".equals(key)) {
          params.setExcludeArchivedResidentsFlag(Boolean.valueOf(value));
        } else if ("-excludeReservedResidentsFlag".equals(key)) {
          params.setExcludeReservedResidentsFlag(Boolean.valueOf(value));
        } else if ("-unReadOnly".equals(key)) {
          params.setUnReadOnly(Boolean.valueOf(value));
        } else {
          logger.error("Unknown params. Please check usage");
        }
      }
    }
    return params;
  }

  /**
   * Method to read config file.
   *
   * @param configFile configFile (not null)
   * @return config properties (not null)
   */
  public static ConfigProperties readConfigFile(String configFile) {
    ConfigProperties configProperties = new ConfigProperties();

    Properties properties = new Properties();
    try {
      FileInputStream fileInputStream = new FileInputStream(configFile);
      properties.load(fileInputStream);
      fileInputStream.close();

      configProperties.setFilePath(properties.getProperty("env.filePath"));
      configProperties.setUrl(properties.getProperty("env.url"));
      configProperties.setUserName(properties.getProperty("env.username"));
      configProperties.setPassWord(properties.getProperty("env.password"));
    } catch (Exception ex) {
      logger.error("Error while reading config file {}", ex);
    }
    return configProperties;
  }
}
