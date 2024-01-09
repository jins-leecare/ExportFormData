/*
 * ResidentDetailsMapDeserializer.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.leecare.extract.model.FieldValue;
import com.leecare.extract.model.RegularFormResponse;
import com.leecare.extract.model.ResidentDetails;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is used for a ResidentDetailsMapDeserializer.
 *
 * @author jjoy
 */
public class ResidentDetailsMapDeserializer extends StdDeserializer<RegularFormResponse> {

  private static final long serialVersionUID = 1L;

  /** Constructs a ResidentDetailsMapDeserializer. */
  public ResidentDetailsMapDeserializer() {
    this(null);
  }

  /**
   * Constructs a ResidentDetailsMapDeserializer.
   *
   * @param vc vc (can be null)
   */
  public ResidentDetailsMapDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public RegularFormResponse deserialize(
      JsonParser aJsonParser, DeserializationContext aDeserializationContext)
      throws IOException, JsonProcessingException {
    JsonNode node = aJsonParser.getCodec().readTree(aJsonParser);
    RegularFormResponse regularFormResponse = new RegularFormResponse();
    Map<Integer, ResidentDetails> resultMap = new LinkedHashMap<>();
    node.fields()
        .forEachRemaining(
            entry -> {
              Integer residentID = Integer.parseInt(entry.getKey());
              ResidentDetails residentDetails = deserializeResidentDetails(entry.getValue());
              resultMap.put(residentID, residentDetails);
            });
    regularFormResponse.setResultMap(resultMap);
    return regularFormResponse;
  }

  private ResidentDetails deserializeResidentDetails(JsonNode aNode) {
    ResidentDetails residentDetails = new ResidentDetails();
    residentDetails.setResidentID(aNode.get("residentID").asInt());
    residentDetails.setFacilityName(aNode.get("facilityName").asText());
    residentDetails.setResidentName(aNode.get("residentName").asText());
    residentDetails.setDateOfBirth(parseDate(aNode.get("dateOfBirth").asText()));
    residentDetails.setNRICNumber(
        aNode.get("NRICNumber") == null ? null : aNode.get("NRICNumber").asText());

    Map<String, FieldValue> fieldValueMap = new LinkedHashMap<>();
    JsonNode fieldValueMapNode = aNode.get("fieldValueMap");
    if (fieldValueMapNode != null && fieldValueMapNode.isObject()) {
      Iterator<Map.Entry<String, JsonNode>> fieldValueFields = fieldValueMapNode.fields();
      while (fieldValueFields.hasNext()) {
        Map.Entry<String, JsonNode> fieldValueEntry = fieldValueFields.next();
        FieldValue fieldValue = deserializeFieldValue(fieldValueEntry.getValue());
        fieldValueMap.put(fieldValueEntry.getKey(), fieldValue);
      }
    }
    residentDetails.setFieldValueMap(fieldValueMap);
    return residentDetails;
  }

  private FieldValue deserializeFieldValue(JsonNode aNode) {
    String fieldValue = aNode.has("value") ? aNode.get("value").asText() : null;
    String fieldName = aNode.has("fieldName") ? aNode.get("fieldName").asText() : null;
    String fieldCaption = aNode.has("fieldCaption") ? aNode.get("fieldCaption").asText() : null;
    return new FieldValue(fieldValue, fieldName, fieldCaption);
  }

  private Date parseDate(String aDateStr) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return dateFormat.parse(aDateStr);
    } catch (ParseException e) {
      return new Date();
    }
  }
}
