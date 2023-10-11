package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.utils.StringUtils;
import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommonCSVDownloader<T> implements CSVDownloader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyyMMdd");

    public void downloadCSV(InputParameters params, String subFolder, String csvName, List<T> rowList, Map<Integer, Resident> residentMap) {
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
                    header.add(field.getName());
                }

                writer.writeNext(header.toArray(new String[0]));

                // Iterate through the list and export each object
                List<String> residentIds = new ArrayList<>();
                for (T obj : rowList) {
                    List<Object> record = new ArrayList<>();
                    if (obj instanceof TasksRow) {
                        TasksRow taskRow = (TasksRow) obj;
                        if (!residentIds.contains(taskRow.getResidentID())) {
                            Resident resident = residentMap.get(Integer.parseInt(taskRow.getResidentID()));
                            record.add(taskRow.getResidentID());
                            record.add("");
                            record.add(taskRow.getResidentName());
                            if (resident != null) {
                                record.add(DATE_FORMAT1.parse(resident.getDateOfBirth()).toString());
                                record.add(resident.getNationalIDNumber());
                            } else {
                                record.add("");
                                record.add("");
                            }
                            residentIds.add(String.valueOf(taskRow.getResidentID()));
                        } else {
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                        }
                    } else if(obj instanceof PersonNoteDetails) {
                        PersonNoteDetails personNoteDetails = (PersonNoteDetails) obj;
                        if (!residentIds.contains(String.valueOf(personNoteDetails.getPersonId()))) {
                            Resident resident = residentMap.get(personNoteDetails.getPersonId());
                            record.add(String.valueOf(personNoteDetails.getPersonId()));
                            record.add("");
                            record.add(personNoteDetails.getResidentName());
                            if (resident != null) {
                                record.add(DATE_FORMAT1.parse(resident.getDateOfBirth()).toString());
                                record.add(resident.getNationalIDNumber());
                            } else {
                                record.add("");
                                record.add("");
                            }
                            residentIds.add(String.valueOf(personNoteDetails.getPersonId()));
                        } else {
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                        }
                    } else if(obj instanceof AdverseReactionDetails) {
                        AdverseReactionDetails adverseReactionDetails = (AdverseReactionDetails) obj;
                        if (!residentIds.contains(String.valueOf(adverseReactionDetails.getResidentId()))) {
                            Resident resident = residentMap.get(adverseReactionDetails.getResidentId());
                            record.add(String.valueOf(adverseReactionDetails.getResidentId()));
                            record.add("");
                            record.add(adverseReactionDetails.getResidentName());
                            if (resident != null) {
                                record.add(DATE_FORMAT1.parse(resident.getDateOfBirth()).toString());
                                record.add(resident.getNationalIDNumber());
                            } else {
                                record.add("");
                                record.add("");
                            }
                            residentIds.add(String.valueOf(adverseReactionDetails.getResidentId()));
                        } else {
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                            record.add("");
                        }
                    }
                    for (Field field : fieldList) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(obj);
                            record.add(value != null ? value.toString() : ""); // Convert to String
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    writer.writeNext(record.toArray(new String[0]));
                }
            } catch (IOException | ParseException e) {
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
