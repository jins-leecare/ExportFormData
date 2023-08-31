package com.leecare.extract.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leecare.extract.model.ConfigProperties;
import com.leecare.extract.model.FileAttachment;
import com.leecare.extract.model.ResidentDetails;
import com.leecare.extract.model.ResidentRecordDetails;

import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataExtractionService {

    RestController restController = new RestController();
    ObjectMapper objectMapper = new ObjectMapper();

    public Map<Integer, ResidentDetails> extractFormData(String facilityId, String jsonBody) {
        String url = ConfigProperties.getUrl() + "/api/v1/facilities/" + Integer.parseInt(facilityId) + "/dataextract/extractFormData";
        ResponseBody responseBody =  restController.postAndRetrieveData(url, jsonBody);
            Map<Integer, ResidentDetails> resultMap = null;
            try {
                TypeReference<Map<Integer, ResidentDetails>> typeReference = new TypeReference<Map<Integer, ResidentDetails>>() {};
                resultMap = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultMap;
        }

    public Map<Integer, ResidentRecordDetails> extractGridFormData(String facilityId, String jsonBody) {
        String url = ConfigProperties.getUrl() + "/api/v1/facilities/" + Integer.parseInt(facilityId) + "/dataextract/extractFormDataForRange";
        ResponseBody responseBody =  restController.postAndRetrieveData(url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        try {
            TypeReference<Map<Integer, ResidentRecordDetails>> typeReference = new TypeReference<Map<Integer, ResidentRecordDetails>>() {};
            resultMap = objectMapper.readValue(responseBody.string(), typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public List<String> extractFormNames(Boolean subForm, Integer facilityId, Boolean gridForm, Boolean customGridForm) {
        String url = ConfigProperties.getUrl() + "/api/v1/facilities/" + facilityId + "/dataextract/extractFormNames";
        String jsonBody = "{" +
                "\"GridForm\":\"" + gridForm + "\" , " +
                "\"SubForm\":\"" + subForm + "\" , " +
                "\"CustomGridForm\": " + customGridForm +
                "}";
        ResponseBody responseBody =  restController.postAndRetrieveData(url, jsonBody);
        List<String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(responseBody.string(), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public Map<String, String> extractFieldCaptionMapping(Integer facilityId) {
        String url = ConfigProperties.getUrl() + "/api/v1/facilities/" + facilityId + "/dataextract/extractFieldCaptionMapping";
        ResponseBody responseBody =  restController.retrieveData(url);
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(responseBody.string(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public List<FileAttachment> extractFileAttachments(Integer facilityId) {
        String url = ConfigProperties.getUrl() + "/api/v1/facilities/" + facilityId + "/dataextract/extractFileAttachments";
        ResponseBody responseBody =  restController.retrieveData(url);
        List<FileAttachment> fileAttachments = null;
        try {
            TypeReference<List<FileAttachment>> typeReference = new TypeReference<List<FileAttachment>>() {};

            fileAttachments = objectMapper.readValue(responseBody.string(), typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileAttachments;
    }
}
