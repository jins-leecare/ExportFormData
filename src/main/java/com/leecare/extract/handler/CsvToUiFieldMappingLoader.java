/*
 * CsvToUiFieldMappingLoader.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This is used for a CsvToUiFieldMappingLoader.
 *
 * @author jjoy
 */
public class CsvToUiFieldMappingLoader {

  private static Map<String, Map<String, String>> mappingMap = new HashMap<>();
  /**
   * Method to load field caption mapping from mapping json file.
   *
   * @param aCsvName csvName
   * @return field caption map
   * @throws IOException io exception
   */
  public static Map<String, String> loadMapping(String aCsvName) throws IOException {
    if (mappingMap.isEmpty()) {
      String resourcePath = "/UiToCsvFieldMapping.json";
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
    return mappingMap.get(aCsvName);
  }
}
