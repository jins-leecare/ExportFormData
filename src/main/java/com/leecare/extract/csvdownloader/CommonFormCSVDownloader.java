package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommonFormCSVDownloader implements CSVDownloader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void downloadCSV(
            InputParameters params,
            String subFolder,
            Map<String, String> fieldNameCaptionMapping,
            String formName,
            Map<Integer, ResidentDetails> residentDetailsList) {
        if (Objects.isNull(residentDetailsList) || residentDetailsList.isEmpty()) {
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading "
                    + formName);
            return;
        }
        System.out.println("CSV Generation started for " + formName + "at " + LocalDateTime.now());
        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");
        String subFolderPath = createFolder(filePath, subFolder);
        CsvSummary summary = new CsvSummary();
        generateCSV(
                residentDetailsList,
                subFolderPath + sanitizeFilename(fieldNameCaptionMapping.getOrDefault(formName, formName)) + ".csv",
                fieldNameCaptionMapping, summary);
        // Print summary to a text file
        printSummaryToFile(summary, filePath, formName);
        System.out.println("CSV Generated successfully for " + formName + "at " + LocalDateTime.now());
        System.out.println();
    }

    public void downloadCSVForRange(
            InputParameters params, String subFolder,
            Map<String, String> fieldNameCaptionMapping,
            String formName,
            Map<Integer, ResidentRecordDetails> residentDetailsMap) {
        if (Objects.isNull(residentDetailsMap) || residentDetailsMap.isEmpty()) {
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading "
                    + formName );
            return;
        }
        CsvSummary summary = new CsvSummary();
        System.out.println("CSV Generation(with range) started for " + formName + "at " + LocalDateTime.now());
        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");

        String subFolderPath = createFolder(filePath, subFolder);
        String csvFilePath = subFolderPath + sanitizeFilename(fieldNameCaptionMapping.getOrDefault(formName, formName)) + ".csv";

        if (!residentDetailsMap.isEmpty()) {

            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                // Write CSV header
                List<String> headerList = new ArrayList<>();
                headerList.add("residentId");
                headerList.add("facilityName");
                headerList.add("residentName");
                headerList.add("dateOfBirth");
                headerList.add("NRICNumber");
                if (formName.equalsIgnoreCase("prescriptions")) {
                    headerList.add("prescriptionDetailsID");
                } else {
                    headerList.add("RecordID");
                }
                headerList.add("DateCreated");

                Set<String> fieldNames = new LinkedHashSet<>();

                for (ResidentRecordDetails resident : residentDetailsMap.values()) {
                    for (String newFieldName : resident.getFieldValueMap().keySet()) {
                        if (!fieldNames.contains(newFieldName)) {
                            String caption = fieldNameCaptionMapping.getOrDefault(newFieldName, newFieldName);
                            boolean captionHasValue = resident.getFieldValueMap().containsKey(newFieldName);

                            if (captionHasValue && fieldNameCaptionMapping.values().stream().filter(value -> value.equals(caption)).count() > 1) {
                                headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                            } else {
                                headerList.add(escapeField(caption));
                            }
                            fieldNames.add(newFieldName);
                        }
                    }
                }

                summary.setNumberOfColumns(headerList.size());
                summary.setNumberOfResidents(residentDetailsMap.size()); // Set the number of residents
                writer.writeNext(headerList.toArray(new String[0]));

                Map<Integer, Integer> columnsWithValuesCount = new HashMap<>();

                // Iterate through the residentDetailsMap
                for (Map.Entry<Integer, ResidentRecordDetails> residentEntry : residentDetailsMap.entrySet()) {
                    Integer residentID = residentEntry.getKey();
                    ResidentRecordDetails residentDetails = residentEntry.getValue();
                    columnsWithValuesCount.put(residentID, 0);

                    // Extract all distinct recordIDs for the current resident
                    TreeSet<Integer> recordIDs = extractAndSortRecordIDs(residentDetails);
                    Boolean residentInserted = false;
                    for (Integer recordID : recordIDs) {
                        List<String> row = new ArrayList<>();
                        if (!residentInserted) {
                            row.add(residentID.toString());
                            row.add(residentDetails.getFacilityName());
                            row.add(residentDetails.getResidentName());
                            row.add(DATE_FORMAT.format(residentDetails.getDateOfBirth()));
                            row.add(residentDetails.getNRICNumber());
                        } else {
                            row.add("");
                            row.add("");
                            row.add("");
                            row.add("");
                            row.add("");
                        }
                        residentInserted = true;
                        row.add(recordID.toString());
                        Map<String, FieldValueDetails> fieldValues = extractFieldValues(residentDetails, recordID, fieldNames);
                        row.add(DATE_FORMAT.format(getRecordDate(fieldValues)));

                        // Get the field values for the current resident and recordID
                        // Add field values to the row
                        for (String fieldName : fieldNames) {
                            String value = getFieldValue(fieldValues.get(fieldName));
                            row.add(escapeField(value));
                            if (value != null && !value.isEmpty()) {
                                columnsWithValuesCount.put(residentID, columnsWithValuesCount.get(residentID) + 1);
                            }
                        }

                        writer.writeNext(row.toArray(new String[0]));
                    }
                }
                summary.setColumnsWithValuesCount(columnsWithValuesCount);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        // Print summary to a text file
        printSummaryToFile(summary, filePath, formName);

        System.out.println("CSV Generated(with range) successfully for " + formName + "at " + LocalDateTime.now());
        System.out.println();
    }

    public void saveFileAttachmentsToFolder(InputParameters params, String subFolderName, List<FileAttachment> fileAttachments) throws IOException {
        String folderPathToForms = createFolder(params.getConfigProperties().getFilePath(), "FORMS");
        String subFolderPath = createFolder(folderPathToForms, subFolderName);
        for (FileAttachment fileAttachment : fileAttachments) {
            String filePath = "";
            if (fileAttachment.getRecordID() != null) {
                filePath = createFolder(subFolderPath, fileAttachment.getRecordID().toString());
            } else {
                filePath = createFolder(subFolderPath, fileAttachment.getFileBatchID().toString());
            }
            byte[] decodedBytes = Base64.getDecoder().decode(fileAttachment.getFileData());
            String newFileName = filePath + fileAttachment.getTitle() + "." + mimeTypeToExtension(fileAttachment.getMimeType());

            saveBase64ToFile(decodedBytes, newFileName);
        }
    }

    private static Date getRecordDate(Map<String, FieldValueDetails> fieldValues) throws ParseException {
        Date date = null;
        for (Map.Entry<String, FieldValueDetails> entry : fieldValues.entrySet()) {
            FieldValueDetails value = entry.getValue();
            if (value != null) {
                // Assuming FieldValueDetails has a method called getDate() to retrieve the date
                date = value.getDateCreated();

                if (date != null) {
                    break; // Exit the loop when a non-null date is found
                }
            }
        }
        if(fieldValues.containsKey("commencedate")) {
            String commenceDate = fieldValues.get("commencedate").getFieldValue();
            if (Objects.isNull(date) && Objects.nonNull(commenceDate)) {
                date = DATE_FORMAT.parse(commenceDate);
            }
        }
        return date;
    }

    private static Set<String> extractFieldNames(Map<Integer, ResidentRecordDetails> residentDetailsMap) {
        Set<String> fieldNames = new LinkedHashSet<>();
        for (ResidentRecordDetails residentDetails : residentDetailsMap.values()) {
            fieldNames.addAll(residentDetails.getFieldValueMap().keySet());
        }
        return fieldNames;
    }

    private static TreeSet<Integer> extractAndSortRecordIDs(ResidentRecordDetails residentDetails) {
        return residentDetails.getFieldValueMap().values().stream()
                .flatMap(recordEntry -> recordEntry.keySet().stream())
                .sorted(new RecordDateComparator(residentDetails))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static Map<String, FieldValueDetails> extractFieldValues(
            ResidentRecordDetails residentDetails,
            Integer recordID,
            Set<String> fieldNames
    ) {
        Map<String, FieldValueDetails> fieldValues = new LinkedHashMap<>();
        for (String fieldName : fieldNames) {
            FieldValueDetails fieldValueDetails = getFieldValuesForRecord(
                    residentDetails.getFieldValueMap(),
                    fieldName,
                    recordID
            );
            fieldValues.put(fieldName, fieldValueDetails);
        }
        return fieldValues;
    }

    private static String getFieldValue(FieldValueDetails fieldValueDetails) {
        if (Objects.nonNull(fieldValueDetails)) {
            if (StringUtils.isNotEmpty(fieldValueDetails.getFieldValue())) {
                return fieldValueDetails.getFieldValue();
            } else if (Objects.nonNull(fieldValueDetails.getValueDate())) {
                return formatDate(fieldValueDetails.getValueDate());
            } else if (Objects.nonNull(fieldValueDetails.getValueBit())) {
                return fieldValueDetails.getValueBit().equals("1") ? "true" : "";
            } else if (Objects.nonNull(fieldValueDetails.getValueNumber()) && fieldValueDetails.getValueNumber() != 0.0) {
                return String.valueOf(fieldValueDetails.getValueNumber());
            }
        }
        return "";
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private static FieldValueDetails getFieldValuesForRecord(
            Map<String, Map<Integer, FieldValueDetails>> fieldValueMap,
            String fieldName,
            Integer recordID
    ) {
        Map<Integer, FieldValueDetails> fieldValuesMap = fieldValueMap.get(fieldName);
        if (fieldValuesMap != null) {
            return fieldValuesMap.get(recordID);
        }
        return null;
    }

    private void generateCSV(
            Map<Integer, ResidentDetails> aResidentDetailsMap,
            String csvFilePath,
            Map<String, String> aFieldNameCaptionMapping, CsvSummary summary) {
        if (!aResidentDetailsMap.isEmpty()) {
            //Check if all residents have the same set of field names
            boolean consistentFieldNames = checkConsistentFieldNames(aResidentDetailsMap);

            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                // Write CSV header
                List<String> headerList = new ArrayList<>();
                headerList.add("residentId");
                headerList.add("facilityName");
                headerList.add("residentName");
                headerList.add("dateOfBirth");
                headerList.add("NRICNumber");

                List<String> fieldNames = new ArrayList<>();

                // Append new field names if consistentFieldNames check fails
                if (consistentFieldNames) {
                    Map<String, String> fieldValueMap =
                            aResidentDetailsMap.values().iterator().next().getFieldValueMap();
                    for (String fieldName : fieldValueMap.keySet()) {
                        String caption = aFieldNameCaptionMapping.getOrDefault(fieldName, fieldName);
                        if (aFieldNameCaptionMapping.values().stream().filter(value -> value.equals(caption)).count() > 1) {
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
                                String caption = aFieldNameCaptionMapping.getOrDefault(newFieldName, newFieldName);
                                boolean captionHasValue = resident.getFieldValueMap().containsKey(newFieldName);

                                if (captionHasValue && aFieldNameCaptionMapping.values().stream().filter(value -> value.equals(caption)).count() > 1) {
                                    headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                                } else {
                                    headerList.add(escapeField(caption));
                                }
                                fieldNames.add(newFieldName);
                            }
                        }
                    }
                }

                summary.setNumberOfColumns(headerList.size());
                summary.setNumberOfResidents(aResidentDetailsMap.size()); // Set the number of residents
                writer.writeNext(headerList.toArray(new String[0]));

                Map<Integer, Integer> columnsWithValuesCount = new HashMap<>();

                // Write resident data
                for (Integer residentId : aResidentDetailsMap.keySet()) {
                    columnsWithValuesCount.put(residentId, 0);
                    ResidentDetails resident = aResidentDetailsMap.get(residentId);
                    List<String> recordList = new ArrayList<>();
                    recordList.add(residentId.toString());
                    recordList.add(resident.getFacilityName());
                    recordList.add(resident.getResidentName());
                    recordList.add(DATE_FORMAT.format(resident.getDateOfBirth()));
                    recordList.add(resident.getNRICNumber());

                    Map<String, String> fieldValueMap = resident.getFieldValueMap();
                    for (String newFieldName : fieldNames) {
                        if (fieldValueMap.containsKey(newFieldName)) {
                            String fieldValue = escapeField(fieldValueMap.get(newFieldName));
                            recordList.add(fieldValue);
                            if (fieldValue != null && !fieldValue.trim().isEmpty()) {
                                columnsWithValuesCount.put(residentId, columnsWithValuesCount.get(residentId) + 1);
                            }
                        }
                    }
                    writer.writeNext(recordList.toArray(new String[0]));
                }
                summary.setColumnsWithValuesCount(columnsWithValuesCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String createFolder(String parentFolderPath, String subfolderName) {
        // Create a Path object for the parent folder
        java.nio.file.Path parentPath = Paths.get(parentFolderPath);
        // Create a Path object for the subfolder
        java.nio.file.Path subfolderPath = parentPath.resolve(subfolderName);
        boolean folderExists = Files.exists(subfolderPath) && Files.isDirectory(subfolderPath);
        if (!folderExists) {
            try {
                // Create the subfolder
                Files.createDirectories(subfolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subfolderPath + "//";
    }

    private static boolean checkConsistentFieldNames(Map<Integer, ResidentDetails> residentsMap) {
        if (residentsMap.isEmpty()) {
            return true; // Empty map is considered consistent
        }

        Map<String, String> referenceFieldValues =
                residentsMap.values().iterator().next().getFieldValueMap();

        for (ResidentDetails resident : residentsMap.values()) {
            Map<String, String> fieldValueMap = resident.getFieldValueMap();
            if (!fieldValueMap.keySet().equals(referenceFieldValues.keySet())) {
                return false; // Inconsistent field names found
            }
        }

        return true; // All residents have consistent field names
    }

    private static String escapeField(String fieldValue) {
        if (StringUtils.isNotEmpty(fieldValue)
                && (fieldValue.contains(",") || fieldValue.contains("\""))) {
            fieldValue = "\"" + fieldValue.replace("\"", "\"\"") + "\"";
        }
        if (StringUtils.isNullOrEmpty(fieldValue)) {
            fieldValue = "";
        }
        return fieldValue;
    }

    private static String sanitizeFilename(String originalFilename) {
        return originalFilename
                .replaceAll("[^a-zA-Z0-9-_\\.]", "_")
                .replaceAll("_+", "_");
    }

    private static void saveBase64ToFile(byte[] bytes, String fileName) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(bytes);
        }
    }

    private static void saveBase64ToFileWithMimeType(String base64Content, String mimeType, String fileName)
            throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("MIME-Type: " + mimeType + "\n");
            writer.write(base64Content);
        }
    }

    private static String mimeTypeToExtension(String mimeType) {
        // This is a simple example, you can map MIME types to extensions as needed
        if (mimeType.equalsIgnoreCase("image/jpg")) {
            return "jpeg";
        } else if (mimeType.equalsIgnoreCase("image/jpeg")) {
            return "jpeg";
        } else if (mimeType.equalsIgnoreCase("image/png")) {
            return "png";
        }
        return "";
    }

    private void printSummaryToFile(CsvSummary summary, String filePath, String formName) {
        String subFolderPath = createFolder(filePath, "SUMMARY");
        String summaryFilePath = subFolderPath + formName + "_summary.txt"; // Specify the path to your text file
        try (PrintWriter writer = new PrintWriter(summaryFilePath)) {
            writer.println("CSV Summary:");
            writer.println("Number of Columns: " + summary.getNumberOfColumns());
            writer.println("Number of Residents: " + summary.getNumberOfResidents());
            writer.println("Columns with Values Count:");

            for (Map.Entry<Integer, Integer> entry : summary.getColumnsWithValuesCount().entrySet()) {
                writer.println("ResidentID: " + entry.getKey() + ", Columns with Values: " + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class RecordDateComparator implements Comparator<Integer> {
        private final ResidentRecordDetails residentDetails;

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

        private FieldValueDetails findFieldValueDetails(Integer recordId) {
            return residentDetails.getFieldValueMap().values().stream()
                    .flatMap(recordEntry -> recordEntry.entrySet().stream())
                    .filter(entry -> entry.getKey().equals(recordId))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(null);
        }
    }
}
