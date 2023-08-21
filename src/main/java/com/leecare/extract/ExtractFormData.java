package com.leecare.extract;

import com.leecare.extract.model.ConfigProperties;
import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.ResidentDetails;
import com.leecare.extract.service.DataExtractionService;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ExtractFormData {
    private static String formName = "";
    private static String facilityId = "";
    private static String configFile = "";
    private static Boolean subForm = false;
    private static Boolean gridForm = false;
    private static Boolean customGridForm = false;
    private static Boolean downloadAttachments = false;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        for (String arg : args) {
            String[] keyValue = arg.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                if ("formName".equals(key)) {
                    formName = value;
                } else if ("facilityId".equals(key)) {
                    facilityId = value;
                } else if ("configFile".equals(key)) {
                    configFile = value;
                } else if ("subForm".equals(key)) {
                    subForm = Boolean.valueOf(value);
                } else if ("gridForm".equals(key)) {
                    gridForm = Boolean.valueOf(value);
                } else if ("customGridForm".equals(key)) {
                    customGridForm = Boolean.valueOf(value);
                } else if ("downloadAttachments".equals(key)) {
                    downloadAttachments = Boolean.valueOf(value);
                } else {
                    System.out.println("Unknown params. Please check usage");
                }
            }
        }

        if (facilityId.equals("") || configFile.equals("")) {
            System.out.println("Usage: java ExtractFormData <OPT>formName=<FormName> <OPT>SubForm=true facilityId=<FacilityId> configFile=<pathToFile>");
            return;
        }

        ConfigProperties.readConfigFile(configFile);
        DataExtractionService dataExtractionService = new DataExtractionService();
        Map<String, String> fieldCaptionMapping = dataExtractionService.extractFieldCaptionMapping(Integer.parseInt(facilityId));
        if (formName.equals("")) {
            List<String> formNames = dataExtractionService.extractFormNames(subForm, Integer.parseInt(facilityId), gridForm, customGridForm);
            formNames.forEach(form -> {
                createCSVForForm(form, fieldCaptionMapping, dataExtractionService);
            });
        } else {
            createCSVForForm(formName, fieldCaptionMapping, dataExtractionService);
        }

        if (downloadAttachments) {
            List<FileAttachment> fileAttachments = dataExtractionService.extractFileAttachments(Integer.parseInt(facilityId));
            saveFileAttachmentsToFolder(fileAttachments);
        }
        System.out.println("CSV files generated");
    }

    private static void saveFileAttachmentsToFolder(List<FileAttachment> fileAttachments) throws IOException {
        String folderPathToForms = createFolder(ConfigProperties.getFilePath(), "FORMS");
        String subFolderPath = createFolder(folderPathToForms, "ATTACHMENTS");
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

    private static void createCSVForForm(String formName, Map<String, String> fieldCaptionMapping, DataExtractionService dataExtractionService) {
        String jsonBody = "{" +
                "\"FormName\":\"" + formName + "\" , " +
                "\"FilePath\":\"" + ConfigProperties.getFilePath() + "\" , " +
                "\"CustomGridForm\": " + customGridForm +
                "}";

        Map<Integer, ResidentDetails> residentDetailsMap = dataExtractionService.extractFormData(facilityId, jsonBody);
        String folderPathToForms = createFolder(ConfigProperties.getFilePath(), "FORMS");
        if (!subForm && !gridForm && !customGridForm) {
            extractToCsvUnderFolder(
                    folderPathToForms,
                    "REGULAR-FORMS",
                    fieldCaptionMapping,
                    formName,
                    residentDetailsMap);
        } else if (subForm) {
            extractToCsvUnderFolder(
                    folderPathToForms,
                    "SUB-FORMS",
                    fieldCaptionMapping,
                    formName,
                    residentDetailsMap);
        } else if (gridForm) {
            extractToCsvUnderFolder(
                    folderPathToForms,
                    "GRID-FORMS",
                    fieldCaptionMapping,
                    formName,
                    residentDetailsMap);
        } else if (customGridForm) {
            extractToCsvUnderFolder(
                    folderPathToForms,
                    "CUSTOM-GRID-FORMS",
                    fieldCaptionMapping,
                    formName,
                    residentDetailsMap);
        }
    }

    private static void extractToCsvUnderFolder(
            String filePath,
            String subFolder,
            Map<String, String> fieldNameCaptionMapping,
            String formName,
            Map<Integer, ResidentDetails> residentDetailsList) {
        String subFolderPath = createFolder(filePath, subFolder);
        generateCSV(
                residentDetailsList,
                subFolderPath + sanitizeFilename(fieldNameCaptionMapping.getOrDefault(formName, formName)) + ".csv",
                fieldNameCaptionMapping);
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
}
