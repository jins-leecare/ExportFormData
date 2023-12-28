package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.leecare.extract.utils.WeightAndVitalUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

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

        generateCSV(
                residentDetailsList,
                subFolderPath + sanitizeFilename(fieldNameCaptionMapping.getOrDefault(formName, formName)) + ".csv",
                fieldNameCaptionMapping);
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
                headerList.add("LoggedByUser");
                if (formName.equalsIgnoreCase("prescriptions")
                        || formName.equalsIgnoreCase("medications")) {
                    headerList.add("prescriptionDetailsID");
                } else {
                    headerList.add("RecordID");
                }
                headerList.add("DateCreated");
                if (formName.equalsIgnoreCase("frmGENVitalSigns")) {
                    headerList.add("(Neurological Observations) Total Score");
                }

                Set<String> fieldNames = new LinkedHashSet<>();

                for (ResidentRecordDetails resident : residentDetailsMap.values()) {
                    for (String newFieldName : resident.getFieldValueMap().keySet()) {
                        if (!fieldNames.contains(newFieldName)) {
                            FieldValueDetails fieldValueDetails = resident.getFieldValueMap()
                                    .get(newFieldName).values().stream().findFirst().get();
                            if (Objects.isNull(fieldValueDetails.getFieldCaption())
                                    || fieldValueDetails.getFieldCaption().equals(fieldValueDetails.getFieldName())) {
                                String caption = fieldNameCaptionMapping.getOrDefault(newFieldName, newFieldName);
                                boolean captionHasValue = resident.getFieldValueMap().containsKey(newFieldName);

                                if (captionHasValue && fieldNameCaptionMapping.values().stream()
                                        .filter(value -> value != null && value.equalsIgnoreCase(caption)).count() > 1) {
                                    headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                                } else {
                                    headerList.add(escapeField(caption));
                                }
                            } else {
                                String caption = fieldValueDetails.getFieldCaption();
                                if (hasDuplicateFieldCaptions(resident.getFieldValueMap(), caption)) {
                                    headerList.add(escapeField(caption) + " (" + escapeField(newFieldName) + ")");
                                } else {
                                    if (formName.equalsIgnoreCase("frmGENVitalSigns")) {
                                        headerList.add(escapeField(fieldNameCaptionMapping.getOrDefault(newFieldName, caption)));
                                    } else {
                                        headerList.add(escapeField(caption));
                                    }

                                }
                            }
                            fieldNames.add(newFieldName);
                        }
                    }
                }

                writer.writeNext(headerList.toArray(new String[0]));

                // Iterate through the residentDetailsMap
                for (Map.Entry<Integer, ResidentRecordDetails> residentEntry : residentDetailsMap.entrySet()) {
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
                        Map<String, FieldValueDetails> fieldValues = extractFieldValues(residentDetails, recordID, fieldNames);
                        row.add(getLoggedBy(fieldValues));
                        row.add(recordID.toString());
                        row.add(DATE_FORMAT.format(getRecordDate(fieldValues)));
                        row.add(getTotalScore(fieldValues));

                        // Get the field values for the current resident and recordID
                        // Add field values to the row
                        for (String fieldName : fieldNames) {
                            String value = getFieldValue(fieldValues.get(fieldName));
                            row.add(escapeField(value));
                        }

                        writer.writeNext(row.toArray(new String[0]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("CSV Generated(with range) successfully for " + formName + "at " + LocalDateTime.now());
        System.out.println();
    }

    private String getTotalScore(Map<String, FieldValueDetails> fieldValues) {
        Object eyeResp = WeightAndVitalUtils.getFieldValueFromHash(fieldValues, WeightAndVitalUtils.NEURO_OBS_EYE_RESP);
        Object verbRep = WeightAndVitalUtils.getFieldValueFromHash(fieldValues, WeightAndVitalUtils.NEURO_OBS_VERB_RESP);
        Object motorResp = WeightAndVitalUtils.getFieldValueFromHash(fieldValues, WeightAndVitalUtils.NEURO_OBS_MOTOR_RESP);
        return String.valueOf(WeightAndVitalUtils.generateGcsScore(
                eyeResp != null ? String.valueOf(eyeResp) : null,
                verbRep != null ? String.valueOf(verbRep) : null,
                motorResp != null ? String.valueOf(motorResp) : null));
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

    public void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formName, InputParameters params) {

        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");
        String subFolderPath = createFolder(filePath, "SUMMARY");
        String csvFilePath = subFolderPath + "summary.csv";

        File summaryCsvFile = new File(csvFilePath);
        try {
            if (Objects.nonNull(residentDetailsMap) && !residentDetailsMap.isEmpty()) {
                if (!summaryCsvFile.exists()) {
                    try {
                        summaryCsvFile.createNewFile();
                        // Initialize the file with header and write residents separately for the first time
                        writeHeaderAndResidentsToCSV(summaryCsvFile, residentDetailsMap);
                    } catch (IOException ex) {
                        ex.printStackTrace();
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

                // Create a map to track the columns for each form name
                Map<String, Integer> columnMap = new LinkedHashMap<>();

                if (!csvData.isEmpty()) {
                    // Read and store existing headers
                    String[] headers = csvData.get(0);
                    for (int i = 0; i < headers.length; i++) {
                        columnMap.put(headers[i], i);
                    }
                }

                if (!columnMap.containsKey(formName)) {
                    // If the formName is not in the headers, add it as a new column
                    columnMap.put(formName, columnMap.size());
                }

                csvData.set(0, columnMap.keySet().toArray(new String[0]));

                for (Integer residentID : residentDetailsMap.keySet()) {
                    if (!containsResidentID(csvData, residentID)) {
                        // The ResidentID is not present in the existing data, so create a new row
                        String[] newRow = new String[columnMap.size()];

                        // Set the ResidentID in the first column
                        newRow[0] = residentID.toString();

                        // Append the new row to the CSV data
                        csvData.add(newRow);
                    }
                }


                // Update the existing data with the new values
                for (int i = 1; i < csvData.size(); i++) {
                    String[] existingRow = csvData.get(i);

                    // Create a new row with one additional column for the formName
                    String[] newRow = new String[existingRow.length + 1];

                    // Copy the existing values to the new row
                    System.arraycopy(existingRow, 0, newRow, 0, existingRow.length);

                    Integer residentId = Integer.parseInt(newRow[columnMap.get("ResidentID")]);

                    Object value = residentDetailsMap.get(residentId);

                    // Update the values in the row based on your requirements
                    // For example, you can update the values under the "formName" column.
                    if (value != null) {
                        if (value instanceof ResidentDetails) {
                            // Handle ResidentDetails
                            ResidentDetails details = (ResidentDetails) value;
                            int totalFields = details.getFieldValueMap().size();
                            int fieldsWithValues = (int) details.getFieldValueMap().values().stream().filter(fieldValue -> fieldValue.getValue() != null).count();
                            //if (i == 1) {
                            newRow[columnMap.get(formName)] = totalFields + " (" + fieldsWithValues + ")";
                            //} else {
                            // newRow[columnMap.get(formName)] = String.valueOf(fieldsWithValues);
                            // }
                        } else if (value instanceof ResidentRecordDetails) {
                            // Handle ResidentRecordDetails
                            ResidentRecordDetails recordDetails = (ResidentRecordDetails) value;
                            int totalFields = recordDetails.getFieldValueMap().size();
                            int fieldsWithValues = (int) recordDetails.getFieldValueMap().values()
                                    .stream()
                                    .flatMap(m -> m.values().stream())
                                    .filter(fieldValue -> checkNonNullValues(fieldValue))
                                    .count();
                            // if (i == 1) {
                            newRow[columnMap.get(formName)] = totalFields + " (" + fieldsWithValues + ")";
                            // } else {
                            //newRow[columnMap.get(formName)] = String.valueOf(fieldsWithValues);
                            //}
                        }
                    }
                    // Replace the existing row with the new row in the list
                    csvData.set(i, newRow);
                }


                // Write the modified data back to the CSV file
                try (CSVWriter csvWriter = new CSVWriter(new FileWriter(summaryCsvFile))) {
                    csvWriter.writeAll(csvData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void writeHeaderAndResidentsToCSV(File file, Map<Integer, ?> residentDetailsMap) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(file))) {
            // Initialize the CSV file with headers
            String[] headers = { "ResidentID" };
            csvWriter.writeNext(headers);

            // Write residents separately for the first time
            for (Map.Entry<Integer, ?> entry : residentDetailsMap.entrySet()) {
                Integer residentId = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    String[] dataRow = new String[1];
                    dataRow[0] = residentId.toString();
                    csvWriter.writeNext(dataRow);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean containsResidentID(List<String[]> csvData, Integer residentID) {
        for (int i = 1; i < csvData.size(); i++) {
            String[] existingRow = csvData.get(i);
            Integer existingResidentID = Integer.parseInt(existingRow[0]);
            if (existingResidentID.equals(residentID)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNonNullValues(FieldValueDetails fieldValue) {
        Boolean nonNullValuePresent = false;
        if (fieldValue.getFieldValue() != null) {
            return true;
        } else if(fieldValue.getValueDate() != null) {
            return true;
        } else if(fieldValue.getValueNumber() != null
                && fieldValue.getValueNumber() != 0.0) {
            return true;
        } else if(fieldValue.getValueBit() != null) {
            return true;
        }
        return nonNullValuePresent;
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

        if(fieldValues.containsKey("signOffTime")) {
            String signOffTime = fieldValues.get("signOffTime").getFieldValue();
            if (Objects.isNull(date) && Objects.nonNull(signOffTime)) {
                date = DATE_FORMAT.parse(signOffTime);
            }
        }
        return date;
    }

    private static String getLoggedBy(Map<String, FieldValueDetails> fieldValues) throws ParseException {
        String loggedBy = null;
        for (Map.Entry<String, FieldValueDetails> entry : fieldValues.entrySet()) {
            FieldValueDetails value = entry.getValue();
            if (value != null) {
                loggedBy = value.getCreatedBy();
                break;
            }
        }
        return loggedBy;
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
                if (fieldValueDetails.getValueBit().equals("1")) {
                    return "TRUE";
                } else if (fieldValueDetails.getValueBit().equals("0")) {
                    return "";
                }
                return "";
            } else if (Objects.nonNull(fieldValueDetails.getValueNumber())) {
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
            Map<String, String> aFieldNameCaptionMapping) {
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
                headerList.add("LastSavedByAndDate");

                List<String> fieldNames = new ArrayList<>();

                // Append new field names if consistentFieldNames check fails
                if (consistentFieldNames) {
                    Map<String, FieldValue> fieldValueMap =
                            aResidentDetailsMap.values().iterator().next().getFieldValueMap();
                    for (String fieldName : fieldValueMap.keySet()) {
                        String caption = fieldValueMap.get(fieldName).getFieldCaption();
                        if (fieldValueMap.values().stream().filter(value -> value.getFieldCaption() != null
                                && value.getFieldCaption().equalsIgnoreCase(caption)).count() > 1) {
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
                                if (resident.getFieldValueMap().values().stream()
                                        .filter(value -> value.getFieldCaption() != null
                                                && value.getFieldCaption().equalsIgnoreCase(caption)).count() > 1) {
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

        Map<String, FieldValue> referenceFieldValues =
                residentsMap.values().iterator().next().getFieldValueMap();

        for (ResidentDetails resident : residentsMap.values()) {
            Map<String, FieldValue> fieldValueMap = resident.getFieldValueMap();
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

    private boolean hasDuplicateFieldCaptions(Map<String, Map<Integer, FieldValueDetails>> fieldValueMap, String caption) {
        Set<String> fieldCaptionsSet = new HashSet<>();

        for (Map<Integer, FieldValueDetails> innerMap : fieldValueMap.values()) {
            FieldValueDetails fieldValueDetails = innerMap.values().stream().findFirst().get();
            String fieldCaption = fieldValueDetails.getFieldCaption();

            // Check for duplicates
            if (fieldCaption != null && fieldCaption.equals(caption)) {
                if (!fieldCaptionsSet.add(fieldCaption)) {
                    // Duplicate found
                    return true;
                }
            }
        }
        // No duplicates found
        return false;
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
