/*
 * CommonFormCSVDownloader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.leecare.extract.utils.WeightAndVitalUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.leecare.extract.utils.ExtractionUtils.*;


/**
 * This is used for a CommonFormCSVDownloader.
 *
 * @author jjoy
 */
public abstract class CommonFormCSVDownloader implements CSVDownloader {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final Logger logger = LogManager.getLogger(CommonFormCSVDownloader.class);

  private static final List<String> formsWithMarkAsResolved
          = Arrays.asList("frmASSFallsSafetyArray", "frmASSBehaviourEvaluationArray",
                          "frm_SG_MSW", "sub_SG_MSW", "frmASSWoundAssessmentArray", "frmGENInfectionArray");
  /**
   * Method to download csv of regular forms
   *
   * @param aParams parameters (not null)
   * @param aSubFolder subfolder name (not null)
   * @param aFieldNameCaptionMapping field caption mapping (can be null)
   * @param aFormName form name (not null)
   * @param aResidentDetailsList resident details list (can be null)
   */
  public void downloadCSV(
      InputParameters aParams,
      String aSubFolder,
      Map<String, String> aFieldNameCaptionMapping,
      String aFormName,
      Map<Integer, ResidentDetails> aResidentDetailsList) {
    if (Objects.isNull(aResidentDetailsList) || aResidentDetailsList.isEmpty()) {
      logger.error(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + aFormName);
      return;
    }
    logger.debug("CSV Generation started for " + aFormName + " at " + LocalDateTime.now());
    String filePath = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");
    String subFolderPath = createFolder(filePath, aSubFolder);

    generateCSV(
        aResidentDetailsList,
        subFolderPath
            + sanitizeFilename(aFieldNameCaptionMapping.getOrDefault(aFormName, aFormName))
            + ".csv",
        aFieldNameCaptionMapping);
    logger.debug("CSV Generated successfully for " + aFormName + " at " + LocalDateTime.now());
  }

  /**
   * Method to download csv for a date range/latest version.
   *
   * @param aParams parameters (not null)
   * @param aSubFolder subfolder name (not null)
   * @param aFieldNameCaptionMapping field caption mapping (can be null)
   * @param aFormName form name (not null)
   * @param aResidentDetailsMap resident details map (can be null)
   */
  public void downloadCSVForRange(
      InputParameters aParams,
      String aSubFolder,
      Map<String, String> aFieldNameCaptionMapping,
      String aFormName,
      Map<Integer, ResidentRecordDetails> aResidentDetailsMap) {
    if (Objects.isNull(aResidentDetailsMap) || aResidentDetailsMap.isEmpty()) {
      logger.debug(
          "Data is not available for export. Please re-evaluate your parameters for downloading "
              + aFormName);
      return;
    }

    logger.debug(
        "CSV Generation(with range) started for " + aFormName + "at " + LocalDateTime.now());
    String filePath = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");

    String subFolderPath = createFolder(filePath, aSubFolder);
    String csvFilePath =
        subFolderPath
            + sanitizeFilename(aFieldNameCaptionMapping.getOrDefault(aFormName, aFormName))
            + (aFormName.equals("frmASSFoodFlSUB") ? "_grid" : "")
            + ".csv";

    if (!aResidentDetailsMap.isEmpty()) {

      try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
        List<String> headerList = new ArrayList<>();
        headerList.add("residentId");
        headerList.add("facilityName");
        headerList.add("residentName");
        headerList.add("dateOfBirth");
        headerList.add("NRICNumber");
        headerList.add("LoggedByUser");
        if (aFormName.equalsIgnoreCase("prescriptions")
            || aFormName.equalsIgnoreCase("medications")) {
          headerList.add("prescriptionDetailsID");
        } else {
          headerList.add("Record ID");
        }
        headerList.add("DateCreated");
        if (formsWithMarkAsResolved.contains(aFormName)) {
          headerList.add("Mark as Resolved");
        }
        if (aFormName.equalsIgnoreCase("frmGENVitalSigns")) {
          headerList.add("(Neurological Observations) Total Score");
        }

        Set<String> fieldNames = new LinkedHashSet<>();

        for (ResidentRecordDetails resident : aResidentDetailsMap.values()) {
          for (String newFieldName : resident.getFieldValueMap().keySet()) {
            boolean addFieldToFieldList;
            if (!fieldNames.contains(newFieldName)) {
              addFieldToFieldList = true;
              FieldValueDetails fieldValueDetails =
                      resident.getFieldValueMap().get(newFieldName).values().stream().findFirst().get();
              if (Objects.isNull(fieldValueDetails.getFieldCaption())
                      || fieldValueDetails.getFieldCaption().equals(fieldValueDetails.getFieldName())) {
                String caption = aFieldNameCaptionMapping.getOrDefault(newFieldName, newFieldName);
                boolean captionHasValue = resident.getFieldValueMap().containsKey(newFieldName);

                if (captionHasValue
                        && aFieldNameCaptionMapping
                        .values()
                        .stream()
                        .filter(value -> value != null && value.equalsIgnoreCase(caption))
                        .count()
                        > 1) {
                  headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                } else {
                  headerList.add(escapeField(caption));
                }
              } else {
                String caption = fieldValueDetails.getFieldCaption();
                if (hasDuplicateFieldCaptions(resident.getFieldValueMap(), caption)) {
                  headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                } else {
                  if (aFormName.equalsIgnoreCase("frmGENVitalSigns")) {
                    headerList.add(
                            escapeField(aFieldNameCaptionMapping.getOrDefault(newFieldName, caption)));
                  } else {
                    String columnName = escapeField(caption);
                    if (!containsElement(headerList, columnName)) {
                      headerList.add(escapeField(caption));
                    } else {
                      addFieldToFieldList = false;
                    }
                  }
                }
              }
              if (addFieldToFieldList) {
                fieldNames.add(newFieldName);
              }
            }
          }
        }

        writer.writeNext(headerList.toArray(new String[0]));

        for (Map.Entry<Integer, ResidentRecordDetails> residentEntry :
            aResidentDetailsMap.entrySet()) {
          Integer residentID = residentEntry.getKey();
          ResidentRecordDetails residentDetails = residentEntry.getValue();

          // Extract all distinct recordIDs for the current resident
          TreeSet<Integer> recordIDs = extractAndSortRecordIDs(residentDetails);
          for (Integer recordID : recordIDs) {
            List<String> row = new ArrayList<>();
            row.add(residentID.toString());
            row.add(residentDetails.getFacilityName());
            row.add(residentDetails.getResidentName());
            row.add(DATE_FORMAT.format(residentDetails.getDateOfBirth()));
            row.add(residentDetails.getNRICNumber());
            Map<String, FieldValueDetails> fieldValues =
                extractFieldValues(residentDetails, recordID, fieldNames);
            row.add(getLoggedBy(fieldValues));
            row.add(recordID.toString());
            row.add(DATE_FORMAT.format(getRecordDate(fieldValues)));
            if (formsWithMarkAsResolved.contains(aFormName)) {
              row.add(getIsMarkAsResolved(fieldValues).toString());
            }
            if (aFormName.equalsIgnoreCase("frmGENVitalSigns")) {
              row.add(getTotalScore(fieldValues));
            }

            // Get the field values for the current resident and recordID
            // Add field values to the row
            for (String fieldName : fieldNames) {
              String value = getFieldValue(fieldValues.get(fieldName));
              row.add(escapeField(value));
            }

            writer.writeNext(row.toArray(new String[0]));
          }
        }
      } catch (IOException ex) {
        logger.error("Caught exception while generating csv {}", ex);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    logger.debug(
        "CSV Generated(with range) successfully for " + aFormName + "at " + LocalDateTime.now());
  }

  private static boolean containsElement(List<String> list, String searchElement) {
    searchElement  = searchElement.replaceAll(":$", "");
    for (String element : list) {
      if (element.equalsIgnoreCase(searchElement)) {
        return true;
      }
    }
    return false;
  }

  private String getTotalScore(Map<String, FieldValueDetails> aFieldValues) {
    Object eyeResp =
        WeightAndVitalUtils.getFieldValueFromHash(
            aFieldValues, WeightAndVitalUtils.NEURO_OBS_EYE_RESP);
    Object verbRep =
        WeightAndVitalUtils.getFieldValueFromHash(
            aFieldValues, WeightAndVitalUtils.NEURO_OBS_VERB_RESP);
    Object motorResp =
        WeightAndVitalUtils.getFieldValueFromHash(
            aFieldValues, WeightAndVitalUtils.NEURO_OBS_MOTOR_RESP);
    return String.valueOf(
        WeightAndVitalUtils.generateGcsScore(
            eyeResp != null ? String.valueOf(eyeResp) : null,
            verbRep != null ? String.valueOf(verbRep) : null,
            motorResp != null ? String.valueOf(motorResp) : null));
  }

  /**
   * Method to add file attachments to folder.
   *
   * @param aParams
   * @param aSubFolderName
   * @param aFileAttachments
   * @throws IOException
   */
  public void saveFileAttachmentsToFolder(
      InputParameters aParams, String aSubFolderName, List<FileAttachment> aFileAttachments)
      throws IOException {
    String folderPathToForms = createFolder(aParams.getConfigProperties().getFilePath(), "FORMS");
    String subFolderPath = createFolder(folderPathToForms, aSubFolderName);
    for (FileAttachment fileAttachment : aFileAttachments) {
      String filePath = "";
      if (fileAttachment.getRecordID() != null) {
        filePath = createFolder(subFolderPath, fileAttachment.getRecordID().toString());
      } else {
        filePath = createFolder(subFolderPath, fileAttachment.getFileBatchID().toString());
      }
      byte[] decodedBytes = Base64.getDecoder().decode(fileAttachment.getFileData());
      String newFileName =
          filePath
              + fileAttachment.getTitle()
              + "."
              + mimeTypeToExtension(fileAttachment.getMimeType());

      saveBase64ToFile(decodedBytes, newFileName);
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
          if (value != null) {
            if (value instanceof ResidentDetails) {
              ResidentDetails details = (ResidentDetails) value;
              int totalFields = details.getFieldValueMap().size();
              int fieldsWithValues =
                      (int)
                              details
                                      .getFieldValueMap()
                                      .values()
                                      .stream()
                                      .filter(fieldValue -> fieldValue.getValue() != null)
                                      .count();
              newRow[columnMap.get(aFormName)] = totalFields + " (" + fieldsWithValues + ")";
            } else if (value instanceof ResidentRecordDetails) {
              // Handle ResidentRecordDetails
              ResidentRecordDetails recordDetails = (ResidentRecordDetails) value;
              int totalFields = filterMap(recordDetails, aFieldMapping).size();
              int fieldsWithValues =
                      (int)
                              filterMap(recordDetails, aFieldMapping)
                                      .values()
                                      .stream()
                                      .flatMap(m -> m.values().stream())
                                      .filter(fieldValue -> checkNonNullValues(fieldValue))
                                      .count();
              newRow[columnMap.get(aFormName)] = totalFields + " (" + fieldsWithValues + ")";
            } else {
              Class<?> objectClass = value.getClass();

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
              for (Field field : fieldList) {
                if (!field.getName().equals("signatureImage")) {
                  totalFields++;
                  field.setAccessible(true);
                  try {
                    Object fieldValue = field.get(value);
                    if (fieldValue != null) {
                      fieldsWithValues++;
                    }
                  } catch (IllegalAccessException ex) {
                    logger.debug("Caught exception whiling downloading csv {}, ", ex);
                  }
                }
              }

              newRow[columnMap.get(aFormName)] = totalFields + " (" + fieldsWithValues + ")";
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

  private static Map<String, Map<Integer, FieldValueDetails>> filterMap(ResidentRecordDetails recordDetails,
                                                                        Map<String, String> aFieldMapping) {
    if (Objects.isNull(aFieldMapping) || aFieldMapping.isEmpty()) {
      return recordDetails.getFieldValueMap();
    } else {
      return recordDetails.getFieldValueMap().entrySet().stream()
              .filter(entry -> aFieldMapping.containsKey(entry.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
  }

  private static Date getRecordDate(Map<String, FieldValueDetails> aFieldValues)
      throws ParseException {
    Date date = null;
    for (Map.Entry<String, FieldValueDetails> entry : aFieldValues.entrySet()) {
      FieldValueDetails value = entry.getValue();
      if (value != null) {
        // Assuming FieldValueDetails has a method called getDate() to retrieve the date
        date = value.getDateCreated();

        if (date != null) {
          break; // Exit the loop when a non-null date is found
        }
      }
    }

    if (aFieldValues.containsKey("commencedate")) {
      String commenceDate = aFieldValues.get("commencedate").getFieldValue();
      if (Objects.isNull(date) && Objects.nonNull(commenceDate)) {
        date = DATE_FORMAT.parse(commenceDate);
      }
    }

    if (aFieldValues.containsKey("signOffTime")) {
      String signOffTime = aFieldValues.get("signOffTime").getFieldValue();
      if (Objects.isNull(date) && Objects.nonNull(signOffTime)) {
        date = DATE_FORMAT.parse(signOffTime);
      }
    }
    return date;
  }

  private static String getLoggedBy(Map<String, FieldValueDetails> aFieldValues)
      throws ParseException {
    String loggedBy = null;
    for (Map.Entry<String, FieldValueDetails> entry : aFieldValues.entrySet()) {
      FieldValueDetails value = entry.getValue();
      if (value != null) {
        loggedBy = value.getCreatedBy();
        break;
      }
    }
    return loggedBy;
  }
  private static Boolean getIsMarkAsResolved(Map<String, FieldValueDetails> aFieldValues)
          throws ParseException {
    Boolean isResolved = null;
    for (Map.Entry<String, FieldValueDetails> entry : aFieldValues.entrySet()) {
      FieldValueDetails value = entry.getValue();
      if (value != null) {
        isResolved = value.isResolved();
        break;
      }
    }
    return isResolved;
  }

  private static Set<String> extractFieldNames(
      Map<Integer, ResidentRecordDetails> aResidentDetailsMap) {
    Set<String> fieldNames = new LinkedHashSet<>();
    for (ResidentRecordDetails residentDetails : aResidentDetailsMap.values()) {
      fieldNames.addAll(residentDetails.getFieldValueMap().keySet());
    }
    return fieldNames;
  }

  private static TreeSet<Integer> extractAndSortRecordIDs(ResidentRecordDetails aResidentDetails) {
    return aResidentDetails
        .getFieldValueMap()
        .values()
        .stream()
        .flatMap(recordEntry -> recordEntry.keySet().stream())
        .sorted(new RecordDateComparator(aResidentDetails))
        .collect(Collectors.toCollection(TreeSet::new));
  }

  private static Map<String, FieldValueDetails> extractFieldValues(
      ResidentRecordDetails aResidentDetails, Integer aRecordID, Set<String> aFieldNames) {
    Map<String, FieldValueDetails> fieldValues = new LinkedHashMap<>();
    for (String fieldName : aFieldNames) {
      FieldValueDetails fieldValueDetails =
          getFieldValuesForRecord(aResidentDetails.getFieldValueMap(), fieldName, aRecordID);
      fieldValues.put(fieldName, fieldValueDetails);
    }
    return fieldValues;
  }

  private static String getFieldValue(FieldValueDetails aFieldValueDetails) {
    if (Objects.nonNull(aFieldValueDetails)) {
      if (StringUtils.isNotEmpty(aFieldValueDetails.getFieldValue())) {
        return aFieldValueDetails.getFieldValue();
      } else if (Objects.nonNull(aFieldValueDetails.getValueDate())) {
        return formatDate(aFieldValueDetails.getValueDate());
      } else if (Objects.nonNull(aFieldValueDetails.getValueBit())) {
        if (aFieldValueDetails.getValueBit().equals("1")) {
          return "TRUE";
        } else if (aFieldValueDetails.getValueBit().equals("0")) {
          return "";
        }
        return "";
      } else if (Objects.nonNull(aFieldValueDetails.getValueNumber())) {
        return String.valueOf(aFieldValueDetails.getValueNumber());
      }
    }
    return "";
  }

  private static String formatDate(Date aDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return dateFormat.format(aDate);
  }

  private static FieldValueDetails getFieldValuesForRecord(
      Map<String, Map<Integer, FieldValueDetails>> aFieldValueMap,
      String aFieldName,
      Integer aRecordID) {
    Map<Integer, FieldValueDetails> fieldValuesMap = aFieldValueMap.get(aFieldName);
    if (fieldValuesMap != null) {
      return fieldValuesMap.get(aRecordID);
    }
    return null;
  }

  private void generateCSV(
      Map<Integer, ResidentDetails> aResidentDetailsMap,
      String aCsvFilePath,
      Map<String, String> aFieldNameCaptionMapping) {
    if (!aResidentDetailsMap.isEmpty()) {
      //Check if all residents have the same set of field names
      boolean consistentFieldNames = checkConsistentFieldNames(aResidentDetailsMap);

      try (CSVWriter writer = new CSVWriter(new FileWriter(aCsvFilePath))) {
        // Write CSV header
        List<String> headerList = new ArrayList<>();
        headerList.add("residentId");
        headerList.add("facilityName");
        headerList.add("residentName");
        headerList.add("dateOfBirth");
        headerList.add("NRICNumber");
        headerList.add("LastSavedByAndDate");

        List<String> fieldNames = new ArrayList<>();

        // Append new field names if consistentFieldNames check fails
        if (consistentFieldNames) {
          Map<String, FieldValue> fieldValueMap =
              aResidentDetailsMap.values().iterator().next().getFieldValueMap();
          for (String fieldName : fieldValueMap.keySet()) {
            String caption = fieldValueMap.get(fieldName).getFieldCaption();
            if (fieldValueMap
                    .values()
                    .stream()
                    .filter(
                        value ->
                            value.getFieldCaption() != null
                                && value.getFieldCaption().equalsIgnoreCase(caption))
                    .count()
                > 1) {
              headerList.add(escapeField(caption) + " (" + escapeField(fieldName) + ")");
            } else {
              headerList.add(escapeField(caption));
            }

            fieldNames.add(fieldName);
          }
        } else {
          for (ResidentDetails resident : aResidentDetailsMap.values()) {
            for (String newFieldName : resident.getFieldValueMap().keySet()) {
              if (!fieldNames.contains(newFieldName)) {
                String caption = resident.getFieldValueMap().get(newFieldName).getFieldCaption();
                if (resident
                        .getFieldValueMap()
                        .values()
                        .stream()
                        .filter(
                            value ->
                                value.getFieldCaption() != null
                                    && value.getFieldCaption().equalsIgnoreCase(caption))
                        .count()
                    > 1) {
                  headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                } else {
                  headerList.add(escapeField(caption));
                }
                fieldNames.add(newFieldName);
              }
            }
          }
        }

        writer.writeNext(headerList.toArray(new String[0]));

        // Write resident data
        for (Integer residentId : aResidentDetailsMap.keySet()) {
          ResidentDetails resident = aResidentDetailsMap.get(residentId);
          List<String> recordList = new ArrayList<>();
          recordList.add(residentId.toString());
          recordList.add(resident.getFacilityName());
          recordList.add(resident.getResidentName());
          recordList.add(DATE_FORMAT.format(resident.getDateOfBirth()));
          recordList.add(resident.getNRICNumber());
          recordList.add(resident.getLastSavedByAndDate());

          Map<String, FieldValue> fieldValueMap = resident.getFieldValueMap();
          for (String newFieldName : fieldNames) {
            if (fieldValueMap.containsKey(newFieldName)) {
              String fieldValue = escapeField(fieldValueMap.get(newFieldName).getValue());
              recordList.add(fieldValue);
            }
          }
          writer.writeNext(recordList.toArray(new String[0]));
        }
      } catch (IOException exception) {
        logger.error("Caught exception while downloading csv {}", exception);
      }
    }
  }

  private static String createFolder(String aParentFolderPath, String aSubfolderName) {
    java.nio.file.Path parentPath = Paths.get(aParentFolderPath);
    java.nio.file.Path subfolderPath = parentPath.resolve(aSubfolderName);
    boolean folderExists = Files.exists(subfolderPath) && Files.isDirectory(subfolderPath);
    if (!folderExists) {
      try {
        // Create the subfolder
        Files.createDirectories(subfolderPath);
      } catch (IOException ex) {
        logger.error("Caught Exception while creating folder {}", ex);
      }
    }
    return subfolderPath + "//";
  }

  private static boolean checkConsistentFieldNames(Map<Integer, ResidentDetails> aResidentsMap) {
    if (aResidentsMap.isEmpty()) {
      return true;
    }
    Map<String, FieldValue> referenceFieldValues =
        aResidentsMap.values().iterator().next().getFieldValueMap();
    for (ResidentDetails resident : aResidentsMap.values()) {
      Map<String, FieldValue> fieldValueMap = resident.getFieldValueMap();
      if (!fieldValueMap.keySet().equals(referenceFieldValues.keySet())) {
        return false;
      }
    }
    return true;
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

  private static void saveBase64ToFile(byte[] aBytes, String aFileName) throws IOException {
    try (FileOutputStream outputStream = new FileOutputStream(aFileName)) {
      outputStream.write(aBytes);
    }
  }

  private static void saveBase64ToFileWithMimeType(
      String aBase64Content, String aMimeType, String aFileName) throws IOException {
    try (FileWriter writer = new FileWriter(aFileName)) {
      writer.write("MIME-Type: " + aMimeType + "\n");
      writer.write(aBase64Content);
    }
  }

  private static String mimeTypeToExtension(String aMimeType) {
    if (aMimeType.equalsIgnoreCase("image/jpg")) {
      return "jpeg";
    } else if (aMimeType.equalsIgnoreCase("image/jpeg")) {
      return "jpeg";
    } else if (aMimeType.equalsIgnoreCase("image/png")) {
      return "png";
    }
    return "";
  }

  private boolean hasDuplicateFieldCaptions(
      Map<String, Map<Integer, FieldValueDetails>> aFieldValueMap, String aCaption) {
    Set<String> fieldCaptionsSet = new HashSet<>();

    for (Map<Integer, FieldValueDetails> innerMap : aFieldValueMap.values()) {
      FieldValueDetails fieldValueDetails = innerMap.values().stream().findFirst().get();
      String fieldCaption = fieldValueDetails.getFieldCaption();

      if (fieldCaption != null && fieldCaption.equals(aCaption)) {
        if (!fieldCaptionsSet.add(fieldCaption)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * This is used for a RecordDateComparator.
   *
   * @author jjoy
   */
  static class RecordDateComparator implements Comparator<Integer> {
    private final ResidentRecordDetails residentDetails;

    /**
     * Constructs a RecordDateComparator.
     *
     * @param residentDetails resident details (can be null)
     */
    public RecordDateComparator(ResidentRecordDetails residentDetails) {
      this.residentDetails = residentDetails;
    }

    @Override
    public int compare(Integer recordId1, Integer recordId2) {
      FieldValueDetails fieldValue1 = findFieldValueDetails(recordId1);
      FieldValueDetails fieldValue2 = findFieldValueDetails(recordId2);

      if (fieldValue1 != null && fieldValue2 != null) {
        Date date1 = fieldValue1.getDateCreated();
        Date date2 = fieldValue2.getDateCreated();
        if (date1 != null && date2 != null) {
          return date1.compareTo(date2);
        }
      }

      return recordId1.compareTo(recordId2);
    }

    private FieldValueDetails findFieldValueDetails(Integer aRecordId) {
      return residentDetails
          .getFieldValueMap()
          .values()
          .stream()
          .flatMap(recordEntry -> recordEntry.entrySet().stream())
          .filter(entry -> entry.getKey().equals(aRecordId))
          .findFirst()
          .map(Map.Entry::getValue)
          .orElse(null);
    }
  }
}
