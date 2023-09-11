package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");
        String subFolderPath = createFolder(filePath, subFolder);
        generateCSV(
                residentDetailsList,
                subFolderPath + sanitizeFilename(fieldNameCaptionMapping.getOrDefault(formName, formName)) + ".csv",
                fieldNameCaptionMapping);
    }

    public void downloadCSVForRange(
            InputParameters params, String subFolder,
            Map<String, String> fieldNameCaptionMapping,
            String formName,
            Map<Integer, ResidentRecordDetails> residentDetailsMap) {

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
                            headerList.add(escapeField(caption));
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
                            row.add(getFieldValue(fieldValues.get(fieldName)));
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
        if (Objects.isNull(date)) {
            date = DATE_FORMAT.parse(fieldValues.get("commencedate").getFieldValue());
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

    private static void generateCSV(
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

                List<String> fieldNames = new ArrayList<>();

                // Append new field names if consistentFieldNames check fails
                if (consistentFieldNames) {
                    Map<String, String> fieldValueMap =
                            aResidentDetailsMap.values().iterator().next().getFieldValueMap();
                    for (String fieldName : fieldValueMap.keySet()) {
                        String caption = aFieldNameCaptionMapping.getOrDefault(fieldName, fieldName);
                        headerList.add(escapeField(caption));
                        fieldNames.add(fieldName);
                    }
                } else {
                    for (ResidentDetails resident : aResidentDetailsMap.values()) {
                        for (String newFieldName : resident.getFieldValueMap().keySet()) {
                            if (!fieldNames.contains(newFieldName)) {
                                String caption = aFieldNameCaptionMapping.getOrDefault(newFieldName, newFieldName);
                                headerList.add(escapeField(caption));
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

                    Map<String, String> fieldValueMap = resident.getFieldValueMap();
                    for (String newFieldName : fieldNames) {
                        if (fieldValueMap.containsKey(newFieldName)) {
                            recordList.add(escapeField(fieldValueMap.get(newFieldName)));
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
