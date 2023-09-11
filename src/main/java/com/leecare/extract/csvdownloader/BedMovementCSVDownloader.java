package com.leecare.extract.csvdownloader;

import com.leecare.extract.model.*;
import com.leecare.extract.service.DataExtractionService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BedMovementCSVDownloader extends CommonFormCSVDownloader {
    private static final SimpleDateFormat DOB_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmm");

    DataExtractionService dataExtractionService;

    public BedMovementCSVDownloader(DataExtractionService dataExtractionService) {
        this.dataExtractionService = dataExtractionService;
    }

    @Override
    public void downloadCSV(InputParameters params) throws IOException, ParseException {
        List<Resident> residentsList = dataExtractionService.extractResidents(params);
        Map<Integer, ResidentRecordDetails> residentDetailsMap = new HashMap<>();
        for (Resident resident : residentsList) {
            List<BedMovement> bedMovements = dataExtractionService.extractBedMovements(params, resident.getId());
            if (Objects.nonNull(bedMovements)) {
                ResidentRecordDetails residentRecordDetails = new ResidentRecordDetails();
                residentRecordDetails.setResidentID(resident.getId());
                residentRecordDetails.setResidentName(resident.getFirstName().concat(" ").concat(resident.getLastName()));
                residentRecordDetails.setDateOfBirth(DOB_FORMAT.parse(resident.getDateOfBirth()));
                residentRecordDetails.setNRICNumber(resident.getNationalIDNumber());
                Map<String, Map<Integer, FieldValueDetails>> fieldValueMap = new HashMap<>();
                fieldValueMap.put("createdByUserID", new HashMap<>());
                fieldValueMap.put("effectiveDateAndTime", new HashMap<>());
                fieldValueMap.put("createdTimestamp", new HashMap<>());
                fieldValueMap.put("bedMovementType", new HashMap<>());
                fieldValueMap.put("leaveType", new HashMap<>());

                for (BedMovement bedMovement : bedMovements) {
                    if (Objects.nonNull(bedMovement.getCreatedByUserID())) {
                        fieldValueMap.get("createdByUserID")
                                .put(bedMovement.getId(), new FieldValueDetails(bedMovement.getId(), String.valueOf(bedMovement.getCreatedByUserID()), getFormattedDate(bedMovement.getCreatedTimestamp()), null));
                    }
                    if (Objects.nonNull(bedMovement.getCreatedByUserID())) {
                        fieldValueMap.get("effectiveDateAndTime")
                                .put(bedMovement.getId(), new FieldValueDetails(bedMovement.getId(), null, getFormattedDate(bedMovement.getCreatedTimestamp()), getFormattedDate(bedMovement.getEffectiveDateAndTime())));
                    }
                    if (Objects.nonNull(bedMovement.getBedMovementType())) {
                        fieldValueMap.get("bedMovementType")
                                .put(bedMovement.getId(), new FieldValueDetails(bedMovement.getId(), bedMovement.getBedMovementType(), getFormattedDate(bedMovement.getCreatedTimestamp()), null));
                    }
                    if (Objects.nonNull(bedMovement.getLeaveType())) {
                        fieldValueMap.get("leaveType")
                                .put(bedMovement.getId(), new FieldValueDetails(bedMovement.getId(), bedMovement.getLeaveType(), getFormattedDate(bedMovement.getCreatedTimestamp()), null));
                    }
                    residentRecordDetails.setFieldValueMap(fieldValueMap);
                }
                residentDetailsMap.put(resident.getId(), residentRecordDetails);
            }
        }
        Map<String, String> fieldCaptionMapping = dataExtractionService.extractFieldCaptionMapping(params);
        super.downloadCSVForRange(params,
                "BED-MOVEMENTS",
                fieldCaptionMapping,
                "bedMovement",
                residentDetailsMap);
    }

    private static Date getFormattedDate(String dateStringValue) throws ParseException {
        try {
            return DATE_FORMAT.parse(dateStringValue);
        } catch (ParseException ex) {
            return DATE_FORMAT2.parse(dateStringValue);
        }
    }
}
