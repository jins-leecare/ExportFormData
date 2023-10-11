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

public class ResidentDetailsMapDeserializer extends StdDeserializer<RegularFormResponse> {

    public ResidentDetailsMapDeserializer() {
        this(null);
    }

    public ResidentDetailsMapDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RegularFormResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        RegularFormResponse regularFormResponse = new RegularFormResponse();
        // Create a new map to hold the result
        Map<Integer, ResidentDetails> resultMap = new LinkedHashMap<>();

        // Iterate through the JSON nodes and deserialize each ResidentDetails
        node.fields().forEachRemaining(entry -> {
            Integer residentID = Integer.parseInt(entry.getKey());
            ResidentDetails residentDetails = deserializeResidentDetails(entry.getValue());
            resultMap.put(residentID, residentDetails);
        });
        regularFormResponse.setResultMap(resultMap);
        return regularFormResponse;
    }

    private ResidentDetails deserializeResidentDetails(JsonNode node) {
        ResidentDetails residentDetails = new ResidentDetails();

        residentDetails.setResidentID(node.get("residentID").asInt());
        residentDetails.setFacilityName(node.get("facilityName").asText());
        residentDetails.setResidentName(node.get("residentName").asText());
        residentDetails.setDateOfBirth(parseDate(node.get("dateOfBirth").asText()));
        residentDetails.setNRICNumber(node.get("NRICNumber") == null ? null : node.get("NRICNumber").asText());

        Map<String, FieldValue> fieldValueMap = new LinkedHashMap<>();
        JsonNode fieldValueMapNode = node.get("fieldValueMap");
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

    private FieldValue deserializeFieldValue(JsonNode node) {
        String fieldValue = node.has("value") ? node.get("value").asText() : null;
        String fieldName = node.has("fieldName") ? node.get("fieldName").asText() : null;
        String fieldCaption = node.has("fieldCaption") ? node.get("fieldCaption").asText() : null;

        return new FieldValue(fieldValue, fieldName, fieldCaption);
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            // Handle the parsing exception or return a default date
            return new Date();
        }
    }
}
