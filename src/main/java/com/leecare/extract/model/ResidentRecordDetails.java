/*
 * ResidentRecordDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is used for a ResidentRecordDetails.
 *
 * @author jjoy
 */
public class ResidentRecordDetails implements Serializable {

  private Integer residentID;
  private String facilityName;
  private String residentName;
  private Date dateOfBirth;
  private String NRICNumber;
  private String loggedByUser;
  private Map<String, Map<Integer, FieldValueDetails>> fieldValueMap = new LinkedHashMap<>();
  /** @return the residentID */
  public Integer getResidentID() {
    return residentID;
  }
  /** @return the facilityName */
  public String getFacilityName() {
    return facilityName;
  }
  /**
   * Sets the facilityName.
   *
   * @param aFacilityName the facilityName to set
   */
  public void setFacilityName(String aFacilityName) {
    facilityName = aFacilityName;
  }
  /** @return the residentName */
  public String getResidentName() {
    return residentName;
  }
  /**
   * Sets the residentName.
   *
   * @param aResidentName the residentName to set
   */
  public void setResidentName(String aResidentName) {
    residentName = aResidentName;
  }
  /** @return the dateOfBirth */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }
  /**
   * Sets the dateOfBirth.
   *
   * @param aDateOfBirth the dateOfBirth to set
   */
  public void setDateOfBirth(Date aDateOfBirth) {
    dateOfBirth = aDateOfBirth;
  }
  /** @return NRICNumber */
  public String getNRICNumber() {
    return NRICNumber;
  }

  /**
   * Sets the NRICNumber.
   *
   * @param aNRICNumber NRICNumber
   */
  public void setNRICNumber(String aNRICNumber) {
    NRICNumber = aNRICNumber;
  }
  /** @return loggedByUser */
  public String getLoggedByUser() {
    return loggedByUser;
  }
  /** @param aLoggedByUser */
  public void setLoggedByUser(String aLoggedByUser) {
    loggedByUser = aLoggedByUser;
  }
  /**
   * Sets the residentID.
   *
   * @param aResidentID the residentID to set
   */
  public void setResidentID(Integer aResidentID) {
    residentID = aResidentID;
  }
  /** @return fieldValueMap */
  public Map<String, Map<Integer, FieldValueDetails>> getFieldValueMap() {
    return fieldValueMap;
  }
  /**
   * Sets the fieldValueMap.
   *
   * @param aFieldValueMap fieldValueMap
   */
  public void setFieldValueMap(Map<String, Map<Integer, FieldValueDetails>> aFieldValueMap) {
    fieldValueMap = aFieldValueMap;
  }
}
