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

public abstract class CommonCSVDownloader implements CSVDownloader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void downloadCSV(
            InputParameters params, String subFolder,
            String csvName,
            List<TasksRow> rowList) {

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
                for (Field field : fields) {
                    header.add(field.getName());
                }

                writer.writeNext(header.toArray(new String[0]));

                // Iterate through the list and export each custom object
                for (TasksRow obj : rowList) {
                    List<Object> record = new ArrayList<>();
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
