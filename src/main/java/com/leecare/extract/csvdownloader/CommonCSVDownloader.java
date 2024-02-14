/*
 * CommonCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.leecare.extract.utils.ExtractionUtils.containsResidentID;
import static com.leecare.extract.utils.ExtractionUtils.writeHeaderAndResidentsToCSV;

/**
 * This is used for a CommonCSVDownloader.
 *
 * @author jjoy
 * @param <T> type parameter
 */
public abstract class CommonCSVDownloader<T> implements CSVDownloader {
  private static final Logger logger = LogManager.getLogger(CommonCSVDownloader.class);
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Method to download data as csv.
   *
   * @param aParams parameters (not null)
   * @param aSubFolder subFolderName (can be null)
   * @param aCsvName csvName (not null)
   * @param aRowList rowlist (can be null)
   * @param aResidentMap resident data map (can be null)
   * @param aFieldMapping field mapping (can be null)
   */
  public void downloadCSV(
      InputParameters aParams,
      String aSubFolder,
      String aCsvName,
      List<T> aRowList,
      Map<Integer, ResidentDetails> aResidentMap,
      Map<String, String> aFieldMapping) {
    if (Objects.isNull(aRowList) || aRowList.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + aCsvName);
      return;
    }
    String filePath = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");

    String subFolderPath = createFolder(filePath, aSubFolder);
    String csvFilePath = subFolderPath + aCsvName + ".csv";

    if (!aRowList.isEmpty()) {
      Class<?> objectClass = aRowList.get(0).getClass();

      // Extract field names using reflection
      Field[] fields = objectClass.getDeclaredFields();
      List<Field> fieldList = new ArrayList<>();
      fieldList.addAll(Arrays.asList(fields));

      objectClass = objectClass.getSuperclass();

      while (objectClass != null) {
        Field[] parentClassFields = objectClass.getDeclaredFields();
        fieldList.addAll(Arrays.asList(parentClassFields));
        objectClass = objectClass.getSuperclass();
      }

      try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

        // Define the CSV header (field names)
        List<String> header = new ArrayList<>();
        header.add("residentId");
        header.add("facilityName");
        header.add("residentName");
        header.add("dateOfBirth");
        header.add("NRICNumber");
        for (Field field : fieldList) {
          if (!field.getName().equals("signatureImage")) {
            if (aFieldMapping != null) {
              if (aFieldMapping.containsKey(field.getName())) {
                header.add(aFieldMapping.get(field.getName()));
              }
            } else {
              header.add(field.getName());
            }
          }
        }

        writer.writeNext(header.toArray(new String[0]));

        for (T obj : aRowList) {
          List<Object> record = new ArrayList<>();
          if (obj instanceof TasksRow) {
            TasksRow taskRow = (TasksRow) obj;

            ResidentDetails resident = aResidentMap.get(taskRow.getResidentID());
            record.add(String.valueOf(taskRow.getResidentID()));
            if (resident != null) {
              record.add(resident.getFacilityName());
            } else {
              record.add("");
            }
            record.add(taskRow.getResidentName());
            if (resident != null) {
              record.add(DATE_FORMAT.format(resident.getDateOfBirth()));
              record.add(resident.getNRICNumber());
            } else {
              record.add("");
              record.add("");
            }
          } else if (obj instanceof PersonNoteDetails) {
            PersonNoteDetails personNoteDetails = (PersonNoteDetails) obj;
            ResidentDetails resident = aResidentMap.get(personNoteDetails.getPersonId());
            record.add(String.valueOf(personNoteDetails.getPersonId()));
            record.add(resident.getFacilityName());
            record.add(personNoteDetails.getResidentName());
            if (resident != null) {
              record.add(DATE_FORMAT.format(resident.getDateOfBirth()));
              record.add(resident.getNRICNumber());
            } else {
              record.add("");
              record.add("");
            }
          } else if (obj instanceof AdverseReactionDetails) {
            AdverseReactionDetails adverseReactionDetails = (AdverseReactionDetails) obj;
            ResidentDetails resident = aResidentMap.get(adverseReactionDetails.getResidentId());
            record.add(String.valueOf(adverseReactionDetails.getResidentId()));
            record.add(resident.getFacilityName());
            record.add(adverseReactionDetails.getResidentName());
            if (resident != null) {
              record.add(DATE_FORMAT.format(resident.getDateOfBirth()));
              record.add(resident.getNRICNumber());
            } else {
              record.add("");
              record.add("");
            }
          }
          for (Field field : fieldList) {
            if (!field.getName().equals("signatureImage")) {
              if (Objects.isNull(aFieldMapping) || aFieldMapping.containsKey(field.getName())) {
                field.setAccessible(true);
                try {
                  Object value = field.get(obj);
                  if (value != null && value instanceof List<?>) {
                    try {
                      List<PersonNoteComments> commentsList = (List<PersonNoteComments>) value;
                      StringBuilder combinedComments = new StringBuilder();
                      for (PersonNoteComments comment : commentsList) {
                        combinedComments
                            .append("\"")
                            .append(comment.getComment())
                            .append("\" (")
                            .append(comment.getCreatedOnForReport())
                            .append(" ")
                            .append(comment.getCreatedUserName())
                            .append("), ");
                      }
                      if (combinedComments.length() > 0) {
                        combinedComments.delete(
                            combinedComments.length() - 2,
                            combinedComments.length()); // Remove the trailing comma and space
                      }
                      record.add(combinedComments.toString());
                    } catch (Exception exception) {
                      logger.debug("Caught exception whiling downloading csv {}, ", exception);
                    }
                  } else {
                    record.add(value != null ? value.toString() : "");
                  }
                } catch (IllegalAccessException ex) {
                  logger.debug("Caught exception whiling downloading csv {}, ", ex);
                }
              }
            }
          }

          writer.writeNext(record.toArray(new String[0]));
        }
      } catch (IOException ex) {
        logger.debug("Caught exception whiling downloading csv {}, ", ex);
      }
    }
  }

  /**
   * Method to download Bed Movements CSV.
   *
   * @param aParams parameters (not null)
   * @param aSubFolderName sub folder name (not null)
   * @param aCsvName csv name (not null)
   * @param aRowList row list (can be null)
   * @param aFieldMapping field mapping (can be null)
   */
  public void downloadCSVFromList(
      InputParameters aParams,
      String aSubFolderName,
      String aCsvName,
      List<T> aRowList,
      Map<String, String> aFieldMapping) {
    if (Objects.isNull(aRowList) || aRowList.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + aCsvName);
      return;
    }
    String filePath = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");

    String subFolderPath = createFolder(filePath, aSubFolderName);
    String csvFilePath = subFolderPath + aCsvName + ".csv";

    if (!aRowList.isEmpty()) {
      Class<?> objectClass = aRowList.get(0).getClass();
      // Extract field names using reflection
      Field[] fields = objectClass.getDeclaredFields();
      List<Field> fieldList = new ArrayList<>();
      fieldList.addAll(Arrays.asList(fields));

      objectClass = objectClass.getSuperclass();

      while (objectClass != null) {
        Field[] parentClassFields = objectClass.getDeclaredFields();
        fieldList.addAll(Arrays.asList(parentClassFields));
        objectClass = objectClass.getSuperclass();
      }

      try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
        List<String> header = new ArrayList<>();
        for (Field field : fieldList) {
          if (aFieldMapping != null) {
            if (aFieldMapping.containsKey(field.getName())) {
              header.add(aFieldMapping.get(field.getName()));
            }
          } else {
            header.add(field.getName());
          }
        }

        writer.writeNext(header.toArray(new String[0]));

        for (T obj : aRowList) {
          List<Object> record = new ArrayList<>();
          for (Field field : fieldList) {
            if (field.getName() != "serialVersionUID") {
              field.setAccessible(true);
              try {
                Object value = field.get(obj);
                record.add(value != null ? value.toString() : "");
              } catch (IllegalAccessException ex) {
                logger.error("Caught exception while dowloading csv {} ", ex);
              }
            }
          }

          writer.writeNext(record.toArray(new String[0]));
        }
      } catch (IOException ex) {
        logger.error("Caught exception while dowloading csv {} ", ex);
      }
    }
  }

  @Override
  public void prepareSummaryCSV(
          Map<Integer, ?> aResidentDetailsMap, String aFormName, InputParameters aParams,
          Map<String, String> aFieldMapping) {

    String filePath = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");
    String subFolderPath = createFolder(filePath, "SUMMARY");
    String csvFilePath = subFolderPath + "summary.csv";

    File summaryCsvFile = new File(csvFilePath);
    try {
      if (Objects.nonNull(aResidentDetailsMap) && !aResidentDetailsMap.isEmpty()) {
        if (!summaryCsvFile.exists()) {
          try {
            summaryCsvFile.createNewFile();
            // Initialize the file with header and write residents separately for the first time
            writeHeaderAndResidentsToCSV(summaryCsvFile, aResidentDetailsMap);
          } catch (IOException ex) {
            logger.error("Caught Exception while creating summary {}", ex);
          }
        }

        List<String[]> csvData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(summaryCsvFile))) {
          csvData = csvReader.readAll();
        } catch (CsvValidationException | IOException e) {
          throw new RuntimeException(e);
        } catch (CsvException e) {
          throw new RuntimeException(e);
        }

        Map<String, Integer> columnMap = new LinkedHashMap<>();

        if (!csvData.isEmpty()) {
          String[] headers = csvData.get(0);
          for (int i = 0; i < headers.length; i++) {
            columnMap.put(headers[i], i);
          }
        }
        if (!columnMap.containsKey(aFormName)) {
          columnMap.put(aFormName, columnMap.size());
        }
        csvData.set(0, columnMap.keySet().toArray(new String[0]));

        for (Integer residentID : aResidentDetailsMap.keySet()) {
          if (!containsResidentID(csvData, residentID)) {
            String[] newRow = new String[columnMap.size()];
            newRow[0] = residentID.toString();
            csvData.add(newRow);
          }
        }

        for (int i = 1; i < csvData.size(); i++) {
          String[] existingRow = csvData.get(i);
          String[] newRow = new String[existingRow.length + 1];
          System.arraycopy(existingRow, 0, newRow, 0, existingRow.length);
          Integer residentId = Integer.parseInt(newRow[columnMap.get("ResidentID")]);
          Object value = aResidentDetailsMap.get(residentId);
          if (value != null && value instanceof List<?>) {
            List<?> list = (List<?>) value;
            if (!list.isEmpty()) {
              Class<?> objectClass = list.get(0).getClass();
              // Extract field names using reflection
              Field[] fields = objectClass.getDeclaredFields();
              List<Field> fieldList = new ArrayList<>();
              fieldList.addAll(Arrays.asList(fields));
              objectClass = objectClass.getSuperclass();

              while (objectClass != null) {
                Field[] parentClassFields = objectClass.getDeclaredFields();
                fieldList.addAll(Arrays.asList(parentClassFields));
                objectClass = objectClass.getSuperclass();
              }
              Integer totalFields = 0;
              Integer fieldsWithValues = 0;
              for (Object record : list) {
                for (Field field : fieldList) {
                  if (Objects.isNull(aFieldMapping) || aFieldMapping.containsKey(field.getName())) {
                    if (!field.getName().equals("signatureImage")) {
                      totalFields++;
                      field.setAccessible(true);
                      try {
                        Object fieldValue = field.get(record);
                        if (fieldValue != null) {
                          fieldsWithValues++;
                        }
                      } catch (IllegalAccessException ex) {
                        logger.debug("Caught exception whiling downloading csv {}, ", ex);
                      }
                    }
                  }
                }
              }
              newRow[columnMap.get(aFormName)] = (totalFields / list.size()) + " (" + fieldsWithValues + ")";
            }
          }
          csvData.set(i, newRow);
        }

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(summaryCsvFile))) {
          csvWriter.writeAll(csvData);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } catch (Exception exception) {
      logger.error("Caught Exception while preparing summary report {}", exception);
    }
  }

  private static String createFolder(String aParentFolderPath, String aSubfolderName) {
    java.nio.file.Path parentPath = Paths.get(aParentFolderPath);
    java.nio.file.Path subfolderPath = parentPath.resolve(aSubfolderName);
    boolean folderExists = Files.exists(subfolderPath) && Files.isDirectory(subfolderPath);
    if (!folderExists) {
      try {
        Files.createDirectories(subfolderPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return subfolderPath + "//";
  }

  private static String escapeField(String aFieldValue) {
    if (StringUtils.isNotEmpty(aFieldValue)
        && (aFieldValue.contains(",") || aFieldValue.contains("\""))) {
      aFieldValue = "\"" + aFieldValue.replace("\"", "\"\"") + "\"";
    }
    if (StringUtils.isNullOrEmpty(aFieldValue)) {
      aFieldValue = "";
    }
    return aFieldValue;
  }

  private static String sanitizeFilename(String aOriginalFilename) {
    return aOriginalFilename.replaceAll("[^a-zA-Z0-9-_\\.]", "_").replaceAll("_+", "_");
  }
}
