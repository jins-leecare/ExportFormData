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

    public void downloadCSV(
            InputParameters params, String subFolder,
            String csvName,
            List<T> rowList, Map<Integer, Resident> residentMap) {
        if (Objects.isNull(rowList) || rowList.isEmpty()) {
            throw new IllegalStateException("Data is not available for export. Please re-evaluate your parameters.");
        }
        String filePath = createFolder(params.getConfigProperties().getFilePath(), "FORMS");

        String subFolderPath = createFolder(filePath, subFolder);
        String csvFilePath = subFolderPath + csvName + ".csv";

        if (!rowList.isEmpty()) {
            Class<?> objectClass = rowList.get(0).getClass();

            // Extract field names using reflection
            Field[] fields = objectClass.getDeclaredFields();

            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

                // Define the CSV header (field names)
                List<String> header = new ArrayList<>();
                header.add("residentId");
                header.add("facilityName");
                header.add("residentName");
                header.add("dateOfBirth");
                header.add("NRICNumber");
                for (Field field : fields) {
                    header.add(field.getName());
                }

                writer.writeNext(header.toArray(new String[0]));

                // Iterate through the list and export each object
                List<String> residentIds = new ArrayList<>();
                for (T obj : rowList) {
                    List<Object> record = new ArrayList<>();
                    TasksRow taskRow = (TasksRow) obj;
                    if (!residentIds.contains(taskRow.getResidentID())) {
                        Resident resident = residentMap.get(Integer.parseInt(taskRow.getResidentID()));
                        record.add(taskRow.getResidentID());
                        record.add("");
                        record.add(taskRow.getResidentName());
                        record.add(DATE_FORMAT1.parse(resident.getDateOfBirth()).toString());
                        record.add(resident.getNationalIDNumber());
                        residentIds.add(taskRow.getResidentID());
                    } else {
                        record.add("");
                        record.add("");
                        record.add("");
                        record.add("");
                        record.add("");
                    }
                    for (Field field : fields) {
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

                System.out.println("CSV file exported successfully to " + filePath);

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

}
