/*
 * DataExtractionService.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.leecare.extract.model.*;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is used for a DataExtractionService.
 *
 * @author jjoy
 */
public class DataExtractionService {
  private static final Logger logger = LogManager.getLogger(DataExtractionService.class);
  private RestController restController = new RestController();

  /**
   * Method to extract formData
   *
   * @param parameters parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return map of residentId and resident details (can be null)
   */
  public Map<Integer, ResidentDetails> extractFormData(
      InputParameters parameters, String jsonBody) {
    logger.debug("DEBUG: DataExtract started for " + jsonBody + " at " + LocalDateTime.now());
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractFormData";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    Map<Integer, ResidentDetails> resultMap = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultMap = response.readEntity(new GenericType<Map<Integer, ResidentDetails>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting form data {}", ex);
      }
    }
    logger.debug(
        "DEBUG: DataExtract Result Obtained for " + jsonBody + " at " + LocalDateTime.now());
    return resultMap;
  }

  /**
   * Method to extract grid/sub form data.
   *
   * @param parameters parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return map of residentId and resident record details (can be null)
   */
  public Map<Integer, ResidentRecordDetails> extractGridFormData(
      InputParameters parameters, String jsonBody) {
    logger.debug(
        "DEBUG: GRID FORM DataExtract started for " + jsonBody + "at " + LocalDateTime.now());
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractFormDataForRange";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    Map<Integer, ResidentRecordDetails> resultMap = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting grid/sub form data {}", ex);
      }
    }
    logger.debug(
        "DEBUG: GRID FORM DataExtract result obtained for "
            + jsonBody
            + "at "
            + LocalDateTime.now());
    return resultMap;
  }

  /**
   * Method to extract form names.
   *
   * @param parameters the input parameters (not null)
   * @return list of form names (can be null)
   */
  public List<String> extractFormNames(InputParameters parameters) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/dataextract/extractFormNames";
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
      } catch (Exception ex) {
        logger.error("Caught exception while extracting form names {}", ex);
      }
    }
    return resultList;
  }

  /**
   * Method to extract field caption mapping.
   *
   * @param parameters parameters (not null)
   * @return map of field and caption (can be null)
   */
  public Map<String, String> extractFieldCaptionMapping(InputParameters parameters) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/dataextract/extractFieldCaptionMapping";
    Response response = restController.retrieveData(parameters, url);
    Map<String, String> resultMap = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultMap = response.readEntity(new GenericType<Map<String, String>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting field caption mapping {}", ex);
      }
    }
    return resultMap;
  }

  /**
   * Method to extract file attachments.
   *
   * @param parameters parameters (not null)
   * @return list of file attachments (can be null)
   */
  public List<FileAttachment> extractFileAttachments(InputParameters parameters) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/dataextract/extractFileAttachments";
    Response response = restController.retrieveData(parameters, url);
    List<FileAttachment> fileAttachments = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        fileAttachments = response.readEntity(new GenericType<List<FileAttachment>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting file attachments {}", ex);
      }
    }
    return fileAttachments;
  }

  /**
   * Method to extract list of residents under a facility.
   *
   * @param parameters input parameters (not null)
   * @return list of residents (can be null)
   */
  public List<Resident> extractResidents(InputParameters parameters) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/residents?"
            + "fields=%5B%22ltcPersonID%22,%22firstName%22,%22lastName%22,%22dateOfBirth%22,%22nationalIDNumber%22%5D";
    Response response = restController.retrieveData(parameters, url);
    List<Resident> residentDetails = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        residentDetails = response.readEntity(new GenericType<List<Resident>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting residents {}", ex);
      }
    }
    return residentDetails;
  }

  /**
   * Method to extract information for a particular resident.
   *
   * @param parameters input parameters (not null)
   * @param residentID residentID (not null)
   * @return resident details (can be null)
   */
  public ResidentDetails extractResident(InputParameters parameters, String residentID) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/dataextract/findResidentDetails/"
            + residentID;
    Response response = restController.retrieveData(parameters, url);
    ResidentDetails residentDetail = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        residentDetail = response.readEntity(new GenericType<ResidentDetails>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting single resident data {}", ex);
      }
    }
    return residentDetail;
  }

  /**
   * Method to extract bed movements report.
   *
   * @param parameters input parameters (not null)
   * @param residentID residentID (not null)
   * @return list of bed movements (can be null)
   */
  public List<BedMovement> extractBedMovements(InputParameters parameters, Integer residentID) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + parameters.getFacilityId()
            + "/residents/"
            + residentID
            + "/bed-movements?"
            + "fields=%5B%22effectiveDateAndTime%22,%22bedMovementType%22,%22leaveType%22,"
            + "%22createdTimestamp%22,%22createdByUserID%22%5D";
    Response response = restController.retrieveData(parameters, url);
    List<BedMovement> bedMovements = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        bedMovements = response.readEntity(new GenericType<List<BedMovement>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting bed movements {}", ex);
      }
    }
    return bedMovements;
  }

  /**
   * Method to extract total bed movements.
   *
   * @param parameters parameters (not null)
   * @return list of total bed movements (can be null)
   */
  public List<TotalBedMovement> extractTotalBedMovements(InputParameters parameters) {
    String jsonBody =
        "{"
            + "\"FromDate\":\""
            + parameters.getFromDate()
            + "\" , "
            + "\"ToDate\":\""
            + parameters.getToDate()
            + "\""
            + "}";
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractTotalMovementReport";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    List<TotalBedMovement> resultList = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultList = response.readEntity(new GenericType<List<TotalBedMovement>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting total bed movements {}", ex);
      }
    }
    return resultList;
  }

  /**
   * Method to extract prescription details.
   *
   * @param parameters parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return map of residentID and resident record details (can be null)
   */
  public Map<Integer, ResidentRecordDetails> extractPrescriptionDetails(
      InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractPrescriptionDetails";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    Map<Integer, ResidentRecordDetails> resultMap = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting prescription details {}", ex);
      }
    }
    return resultMap;
  }

  /**
   * Method to extract medication details.
   *
   * @param parameters input parameters (not null)
   * @param jsonBody jsonBody (can be null)
   * @return map of residentID and resident record details (can be null)
   */
  public Map<Integer, ResidentRecordDetails> extractMedicationsDetails(
      InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractMedicationsDetails";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    Map<Integer, ResidentRecordDetails> resultMap = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultMap = response.readEntity(new GenericType<Map<Integer, ResidentRecordDetails>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting medication details {}", ex);
      }
    }
    return resultMap;
  }

  /**
   * Method to extract task details.
   *
   * @param parameters input parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return list of tasks (can be null)
   */
  public List<TasksRow> extractTaskDetails(InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractEvents";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    List<TasksRow> resultList = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultList = response.readEntity(new GenericType<List<TasksRow>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting task details {}", ex);
      }
    }
    return resultList;
  }

  /**
   * Method to extract progress notes.
   *
   * @param parameters input parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return list of progress notes (can be null)
   */
  public List<PersonNoteDetails> extractProgressNotes(InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractProgressNotes";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    List<PersonNoteDetails> resultList = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        resultList =
            objectMapper.readValue(
                response.readEntity(String.class), new TypeReference<List<PersonNoteDetails>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting progress notes details {}", ex);
      }
    }
    return resultList;
  }

  /**
   * Method to extract adverse reactions.
   *
   * @param parameters parameters (not null)
   * @param jsonBody jsonBody (not null)
   * @return list of adverse reaction details (can be null)
   */
  public List<AdverseReactionDetails> extractAdverseReactions(
      InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
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

  /**
   * Method to extract SDD Medications master list.
   *
   * @param parameters input parameters (not null)
   * @return list of medication data (can be null)
   */
  public List<MedicationData> extractSDDMedications(InputParameters parameters) {
    String jsonBody = "{}";
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/dataextract/extractSDDMedications";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    List<MedicationData> resultList = null;
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      try {
        resultList = response.readEntity(new GenericType<List<MedicationData>>() {});
      } catch (Exception ex) {
        logger.error("Caught exception while extracting SDD medication details {}", ex);
      }
    }
    return resultList;
  }

  /**
   * Method to download resident documents.
   *
   * @param parameters parameters (not null)
   * @param jsonBody jsonBody (can be null)
   * @return response response (can be null)
   */
  public Response downloadResidentDocuments(InputParameters parameters, String jsonBody) {
    String url =
        parameters.getConfigProperties().getUrl()
            + "/api/v1/facilities/"
            + Integer.parseInt(parameters.getFacilityId())
            + "/residents/documents";
    Response response = restController.postAndRetrieveData(parameters, url, jsonBody);
    if (Objects.nonNull(response) && response.getStatus() == Response.Status.OK.getStatusCode()) {
      return response;
    }
    return null;
  }
}
