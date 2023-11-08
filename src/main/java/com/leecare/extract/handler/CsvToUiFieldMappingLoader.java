package com.leecare.extract.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CsvToUiFieldMappingLoader {

    private static Map<String, Map<String, String>> mappingMap = new HashMap<>();
    public static Map<String, String> loadMapping(String csvName) throws IOException {
        if(mappingMap.isEmpty()) {
            String resourcePath = "/UiToCsvFieldMapping.json";

            // Use the ClassLoader to load the resource as an InputStream
            InputStream inputStream = CsvToUiFieldMappingLoader.class.getResourceAsStream(resourcePath);

            if (inputStream == null) {
                System.out.println("Mapping file not found: " + resourcePath);
                return new HashMap<>();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            if (rootNode.isArray()) {
                for (JsonNode formMappingNode : rootNode) {
                    String formName = formMappingNode.get("formName").asText();
                    JsonNode fieldsNode = formMappingNode.get("fields");

                    Map<String, String> fieldMapping = new HashMap<>();
                    if (fieldsNode.isArray()) {
                        for (JsonNode fieldNode : fieldsNode) {
                            String originalName = fieldNode.get("fieldName").asText();
                            String renamedName = fieldNode.get("renamedFieldName").asText();
                            fieldMapping.put(originalName, renamedName);
                        }
                    }

                    mappingMap.put(formName, fieldMapping);
                }
            }
        }
        return mappingMap.get(csvName);
    }
}
