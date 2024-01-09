/*
 * ResidentDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is used for a ResidentDetails.
 *
 * @author jjoy
 */
public class ResidentDetails implements Serializable {

  private Integer residentID;
  private String facilityName;
  private String residentName;
  private Date dateOfBirth;
  private String NRICNumber;
  private String lastSavedByAndDate;
  private Map<String, FieldValue> fieldValueMap = new LinkedHashMap<>();
  /** @return the residentID */
  public Integer getResidentID() {
    return residentID;
  }
  /**
   * Sets the residentID.
   *
   * @param aResidentID the residentID to set
   */
  public void setResidentID(Integer aResidentID) {
    residentID = aResidentID;
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
  /** @return the nRICNumber */
  public String getNRICNumber() {
    return NRICNumber;
  }
  /**
   * Sets the nRICNumber.
   *
   * @param aNRICNumber the nRICNumber to set
   */
  public void setNRICNumber(String aNRICNumber) {
    NRICNumber = aNRICNumber;
  }
  /** @return the lastSavedByAndDate */
  public String getLastSavedByAndDate() {
    return lastSavedByAndDate;
  }
  /**
   * Sets the lastSavedByAndDate.
   *
   * @param aLastSavedByAndDate the lastSavedByAndDate to set
   */
  public void setLastSavedByAndDate(String aLastSavedByAndDate) {
    lastSavedByAndDate = aLastSavedByAndDate;
  }
  /** @return the fieldValueMap */
  public Map<String, FieldValue> getFieldValueMap() {
    return fieldValueMap;
  }
  /**
   * Sets the fieldValueMap.
   *
   * @param aFieldValueMap the fieldValueMap to set
   */
  public void setFieldValueMap(Map<String, FieldValue> aFieldValueMap) {
    fieldValueMap = aFieldValueMap;
  }
}
