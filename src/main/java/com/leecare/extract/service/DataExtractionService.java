package com.leecare.extract.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.leecare.extract.model.*;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataExtractionService {
    RestController restController = new RestController();

    public Map<Integer, ResidentDetails> extractFormData(InputParameters parameters, String jsonBody) {
        System.out.println("DEBUG: DataExtract started for " + jsonBody + "at " + LocalDateTime.now());
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractFormData";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentDetails> resultMap = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultMap = response.readEntity(new GenericType<Map<Integer, ResidentDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("DEBUG: DataExtract Result Obtained for " + jsonBody + "at " + LocalDateTime.now());
        return resultMap;
    }

    public Map<Integer, ResidentRecordDetails> extractGridFormData(InputParameters parameters, String jsonBody) {
        System.out.println("DEBUG: GRID FORM DataExtract started for " + jsonBody + "at "  + LocalDateTime.now());
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractFormDataForRange";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("DEBUG: GRID FORM DataExtract result obtained for " + jsonBody + "at " + LocalDateTime.now());
        return resultMap;
    }

    public List<String> extractFormNames(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFormNames";
        String jsonBody = "{" +
                "\"GridForm\":\"" + parameters.getGridForm() + "\" , " +
                "\"SubForm\":\"" + parameters.getSubForm() + "\" , " +
                "\"CustomGridForm\":\"" + parameters.getCustomGridForm() + "\" , " +
                "\"RegularForm\":\"" + parameters.getRegularForm() + "\"" +
                "}";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<String> resultList = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultList = response.readEntity(new GenericType<List<String>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public Map<String, String> extractFieldCaptionMapping(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFieldCaptionMapping";
        Response response = restController.retrieveData(parameters, url);
        Map<String, String> resultMap = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultMap = response.readEntity(new GenericType<Map<String, String>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<FileAttachment> extractFileAttachments(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/dataextract/extractFileAttachments";
        Response response = restController.retrieveData(parameters, url);
        List<FileAttachment> fileAttachments = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                fileAttachments = response.readEntity(new GenericType<List<FileAttachment>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileAttachments;
    }

    public List<Resident> extractResidents(InputParameters parameters) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/residents?"
                + "fields=%5B%22ltcPersonID%22,%22firstName%22,%22lastName%22,%22dateOfBirth%22,%22nationalIDNumber%22%5D";
        Response response = restController.retrieveData(parameters, url);
        List<Resident> residentDetails = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                residentDetails = response.readEntity(new GenericType<List<Resident>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return residentDetails;
    }

    public ResidentDetails extractResident(InputParameters parameters, String residentID) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId()
                + "/dataextract/findResidentDetails/"
                + residentID;
        Response response = restController.retrieveData(parameters, url);
        ResidentDetails residentDetail = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                residentDetail = response.readEntity(new GenericType<ResidentDetails>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return residentDetail;
    }

    public List<BedMovement> extractBedMovements(InputParameters parameters, Integer residentID) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + parameters.getFacilityId() + "/residents/"
                + residentID + "/bed-movements?"
                + "fields=%5B%22effectiveDateAndTime%22,%22bedMovementType%22,%22leaveType%22,%22createdTimestamp%22,%22createdByUserID%22%5D";
        Response response = restController.retrieveData(parameters, url);
        List<BedMovement> bedMovements = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                bedMovements = response.readEntity(new GenericType<List<BedMovement>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bedMovements;
    }

    public Map<Integer, ResidentRecordDetails> extractPrescriptionDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId()) + "/dataextract/extractPrescriptionDetails";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public Map<Integer, ResidentRecordDetails> extractMedicationsDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractMedicationsDetails";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        Map<Integer, ResidentRecordDetails> resultMap = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<TasksRow> extractTaskDetails(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractEvents";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<TasksRow> resultList = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultList = response.readEntity(new GenericType<List<TasksRow>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public List<PersonNoteDetails> extractProgressNotes(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractProgressNotes";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<PersonNoteDetails> resultList = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

                resultList = objectMapper.readValue(response.readEntity(String.class),
                        new TypeReference<List<PersonNoteDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }


    public List<AdverseReactionDetails> extractAdverseReactions(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/dataextract/extractAdverseReactions";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<AdverseReactionDetails> resultList = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
            try {
                resultList = response.readEntity(new GenericType<List<AdverseReactionDetails>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public Response downloadResidentDocuments(InputParameters parameters, String jsonBody) {
        String url = parameters.getConfigProperties().getUrl() + "/api/v1/facilities/" + Integer.parseInt(parameters.getFacilityId())
                + "/residents/documents";
        Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
        List<PersonNoteDetails> resultList = null;
        if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
          return response;
        }
        return null;
    }
}
