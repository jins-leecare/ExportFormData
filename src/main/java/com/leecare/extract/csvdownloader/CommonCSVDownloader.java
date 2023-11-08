package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class CommonCSVDownloader<T> implements CSVDownloader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public void downloadCSV(InputParameters params, String subFolder, String csvName,
                            List<T> rowList, Map<Integer, ResidentDetails> residentMap,
                            Map<String, String> fieldMapping) {
        if (Objects.isNull(rowList) || rowList.isEmpty()) {
            System.out.println("Data is not available for export. Please re-evaluate your parameters for downloading " + csvName);
            return;
        }
        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");

        String subFolderPath = createFolder(filePath, subFolder);
        String csvFilePath = subFolderPath + csvName + ".csv";

        if (!rowList.isEmpty()) {
            Class<?> objectClass = rowList.get(0).getClass();

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
                    if (fieldMapping != null) {
                        if (fieldMapping.containsKey(field.getName())) {
                            header.add(fieldMapping.get(field.getName()));
                        }
                    } else {
                        header.add(field.getName());
                    }
                }

                writer.writeNext(header.toArray(new String[0]));

                for (T obj : rowList) {
                    List<Object> record = new ArrayList<>();
                    if (obj instanceof TasksRow) {
                        TasksRow taskRow = (TasksRow) obj;

                        ResidentDetails resident = residentMap.get(taskRow.getResidentID());
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
                        ResidentDetails resident = residentMap.get(personNoteDetails.getPersonId());
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
                        ResidentDetails resident = residentMap.get(adverseReactionDetails.getResidentId());
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
                        if (Objects.isNull(fieldMapping) || fieldMapping.containsKey(field.getName())) {
                            field.setAccessible(true);
                            try {
                                Object value = field.get(obj);
                                if (value != null && value instanceof List<?>) {
                                    try {
                                        List<PersonNoteComments> commentsList = (List<PersonNoteComments>) value;
                                        StringBuilder combinedComments = new StringBuilder();
                                        for (PersonNoteComments comment : commentsList) {
                                            combinedComments.append("\"").append(comment.getComment()).append("\" (").append(comment.getCreatedOnForReport()).append("), ");
                                        }
                                        if (combinedComments.length() > 0) {
                                            combinedComments.delete(combinedComments.length() - 2, combinedComments.length()); // Remove the trailing comma and space
                                        }
                                        record.add(combinedComments.toString());
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                } else {
                                    record.add(value != null ? value.toString() : ""); // Convert to String
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    writer.writeNext(record.toArray(new String[0]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void prepareSummaryCSV(Map<Integer, ?> residentDetailsMap, String formName, InputParameters params) {

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

    private static String escapeField(String fieldValue) {
        if (StringUtils.isNotEmpty(fieldValue) && (fieldValue.contains(",") || fieldValue.contains("\""))) {
            fieldValue = "\"" + fieldValue.replace("\"", "\"\"") + "\"";
        }
        if (StringUtils.isNullOrEmpty(fieldValue)) {
            fieldValue = "";
        }
        return fieldValue;
    }

    private static String sanitizeFilename(String originalFilename) {
        return originalFilename.replaceAll("[^a-zA-Z0-9-_\\.]", "_").replaceAll("_+", "_");
    }

}
