package com.leecare.extract.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leecare.extract.model.*;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataExtractionService {
    RestController restController = new RestController();
    ObjectMapper objectMapper = new ObjectMapper();

    public Map<Integer, ResidentDetails> extractFormData(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractFormData";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentDetails> resultMap = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<Map<Integer, ResidentDetails>> typeReference = new TypeReference<Map<Integer, ResidentDetails>>() {
                };
                resultMap = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public Map<Integer, ResidentRecordDetails> extractGridFormData(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractFormDataForRange";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<Map<Integer, ResidentRecordDetails>> typeReference = new TypeReference<Map<Integer, ResidentRecordDetails>>() {
                };
                resultMap = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<String> extractFormNames(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFormNames";
        String jsonBody = "{" +
                "\"GridForm\":\"" + parameters.getGridForm() + "\" , " +
                "\"SubForm\":\"" + parameters.getSubForm() + "\" , " +
                "\"CustomGridForm\": " + parameters.getCustomGridForm() +
                "}";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<String> resultList = null;
        if (Objects.nonNull(responseBody)) {
            try {
                resultList = objectMapper.readValue(responseBody.string(), List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public Map<String, String> extractFieldCaptionMapping(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFieldCaptionMapping";
        ResponseBody responseBody = restController.retrieveData(parameters, url);
        Map<String, String> resultMap = null;
        if (Objects.nonNull(responseBody)) {
            try {
                resultMap = objectMapper.readValue(responseBody.string(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<FileAttachment> extractFileAttachments(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFileAttachments";
        ResponseBody responseBody = restController.retrieveData(parameters, url);
        List<FileAttachment> fileAttachments = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<List<FileAttachment>> typeReference = new TypeReference<List<FileAttachment>>() {
                };

                fileAttachments = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileAttachments;
    }

    public List<Resident> extractResidents(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/residents?"
                + "fields=%5B\"ltcPersonID\",\"firstName\",\"lastName\",\"dateOfBirth\",\"nationalIDNumber\"%5D";
        ResponseBody responseBody = restController.retrieveData(parameters, url);
        List<Resident> residentDetails = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<List<Resident>> typeReference = new TypeReference<List<Resident>>() {
                };
                residentDetails = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return residentDetails;
    }

    public Resident extractResident(InputParameters parameters, String residentID) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/residents/"
                + residentID + "?"
                + "fields=%5B\"ltcPersonID\",\"firstName\",\"lastName\",\"dateOfBirth\",\"nationalIDNumber\"%5D";
        ResponseBody responseBody = restController.retrieveData(parameters, url);
        Resident residentDetail = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<Resident> typeReference = new TypeReference<Resident>() {
                };
                residentDetail = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return residentDetail;
    }

    public List<BedMovement> extractBedMovements(InputParameters parameters, Integer residentID) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/residents/"
                + residentID + "/bed-movements?"
                + "fields=%5B\"effectiveDateAndTime\",\"bedMovementType\",\"leaveType\",\"createdTimestamp\",\"createdByUserID\"%5D";
        ResponseBody responseBody = restController.retrieveData(parameters, url);

        List<BedMovement> bedMovements = null;
        if (Objects.nonNull(responseBody)) {
            try {
                String content = responseBody.string();
                TypeReference<List<BedMovement>> typeReference = new TypeReference<List<BedMovement>>() {
                };
                bedMovements = objectMapper.readValue(content, typeReference);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bedMovements;
    }

    public Map<Integer, ResidentRecordDetails> extractPrescriptionDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractPrescriptionDetails";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<Map<Integer, ResidentRecordDetails>> typeReference = new TypeReference<Map<Integer, ResidentRecordDetails>>() {
                };
                resultMap = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public Map<Integer, ResidentRecordDetails> extractMedicationsDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractMedicationsDetails";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<Map<Integer, ResidentRecordDetails>> typeReference = new TypeReference<Map<Integer, ResidentRecordDetails>>() {
                };
                resultMap = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<TasksRow> extractTaskDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractEvents";
        ResponseBody responseBody = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<TasksRow> resultList = null;
        if (Objects.nonNull(responseBody)) {
            try {
                TypeReference<List<TasksRow>> typeReference = new TypeReference<List<TasksRow>>() {
                };
                resultList = objectMapper.readValue(responseBody.string(), typeReference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
