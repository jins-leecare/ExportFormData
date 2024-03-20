/*
 * FieldValueDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;


/**
 * This is used for a {@code FieldValueDetails}.
 *
 * @author Jins Joy
 */
public class FieldValueDetails implements Serializable {
  private Integer recordID;
  private String fieldValue;
  private Date dateCreated;
  private String createdBy;
  private Integer batchID;
  private Integer schemaID;
  private Date valueDate;
  private Double valueNumber = null;
  private String valueBit;
  private String fieldName;
  private String fieldCaption;
  private boolean isResolved;

  public FieldValueDetails() {
  }

  /**
   * Constructs a FieldValueDetails.
   *
   * @param aRecordID recordId (can be null)
   * @param aIsResolved is marked as resolved
   * @param aFieldValue fieldValue (can be null)
   * @param aDateCreated dateCreated (can be null)
   * @param aCreatedBy createdBy (can be null)
   * @param aBatchID batchID (can be null)
   * @param aSchemaID schemaID(can be null)
   * @param aValueDate valueDate (can be null)
   * @param aValueNumber valueNumber (can be null)
   * @param aValueBit valueBit (can be null)
   * @param aFieldName fieldName (can be null)
   * @param aFieldCaption fieldCaption (can be null)
   */
  public FieldValueDetails(Integer aRecordID, boolean aIsResolved, String aFieldValue,
                           Date aDateCreated, String aCreatedBy, Integer aBatchID, Integer aSchemaID, Date aValueDate,
                           Double aValueNumber, String aValueBit, String aFieldName, String aFieldCaption) {
    super();
    recordID = aRecordID;
    isResolved = aIsResolved;
    fieldValue = aFieldValue;
    dateCreated = aDateCreated;
    createdBy = aCreatedBy;
    batchID = aBatchID;
    schemaID = aSchemaID;
    valueDate = aValueDate;
    valueNumber = aValueNumber;
    valueBit = aValueBit;
    fieldName = aFieldName;
    fieldCaption = aFieldCaption;
  }

  /**
   * Constructs a FieldValueDetails.
   *
   * @param aRecordID recordID (not null)
   * @param aFieldValue fieldValue (not null)
   */
  public FieldValueDetails(Integer aRecordID, String aFieldValue) {
    super();
    recordID = aRecordID;
    fieldValue = aFieldValue;
  }

  public FieldValueDetails(Integer recordID, String fieldValue, Date dateCreated, Date valueDate) {
    this.recordID = recordID;
    this.fieldValue = fieldValue;
    this.dateCreated = dateCreated;
    this.valueDate = valueDate;
  }

  /**
   * Gets the recordID.
   *
   * @return the recordID (not null)
   */
  public Integer getRecordID() {
    return recordID;
  }

  /**
   * Sets the recordID.
   *
   * @param aRecordID the recordID to set (not null)
   */
  public void setRecordID(Integer aRecordID) {
    recordID = aRecordID;
  }

  /**
   * Gets the fieldValue.
   *
   * @return the fieldValue (not null)
   */
  public String getFieldValue() {
    return fieldValue;
  }

  /**
   * Sets the fieldValue.
   *
   * @param aFieldValue the fieldValue to set (not null)
   */
  public void setFieldValue(String aFieldValue) {
    fieldValue = aFieldValue;
  }

  /**
   * Gets the dateCreated.
   *
   * @return the dateCreated (not null)
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the dateCreated.
   *
   * @param aDateCreated the dateCreated to set (not null)
   */
  public void setDateCreated(Date aDateCreated) {
    dateCreated = aDateCreated;
  }

  /**
   * Gets the createdBy.
   *
   * @return the createdBy (not null)
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the createdBy.
   *
   * @param aCreatedBy the createdBy to set (not null)
   */
  public void setCreatedBy(String aCreatedBy) {
    createdBy = aCreatedBy;
  }

  /**
   * Gets the batchID.
   *
   * @return the batchID (not null)
   */
  public Integer getBatchID() {
    return batchID;
  }

  /**
   * Sets the batchID.
   *
   * @param aBatchID the batchID to set (not null)
   */
  public void setBatchID(Integer aBatchID) {
    batchID = aBatchID;
  }

  /**
   * Gets the schemaID.
   *
   * @return the schemaID (not null)
   */
  public Integer getSchemaID() {
    return schemaID;
  }

  /**
   * Sets the schemaID.
   *
   * @param aSchemaID the schemaID to set (not null)
   */
  public void setSchemaID(Integer aSchemaID) {
    schemaID = aSchemaID;
  }

  /**
   * Gets the valueDate.
   *
   * @return the valueDate (not null)
   */
  public Date getValueDate() {
    return valueDate;
  }

  /**
   * Sets the valueDate.
   *
   * @param aValueDate the valueDate to set (not null)
   */
  public void setValueDate(Date aValueDate) {
    valueDate = aValueDate;
  }

  /**
   * Gets the valueNumber.
   *
   * @return the valueNumber (not null)
   */
  public Double getValueNumber() {
    return valueNumber;
  }

  /**
   * Sets the valueNumber.
   *
   * @param aValueNumber the valueNumber to set (not null)
   */
  public void setValueNumber(Double aValueNumber) {
    valueNumber = aValueNumber;
  }

  /**
   * Gets the valueBit.
   *
   * @return the valueBit (not null)
   */
  public String getValueBit() {
    return valueBit;
  }

  /**
   * Sets the valueBit.
   *
   * @param aValueBit the valueBit to set (not null)
   */
  public void setValueBit(String aValueBit) {
    valueBit = aValueBit;
  }

  /**
   * Gets the fieldName.
   *
   * @return the fieldName (not null)
   */
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Sets the fieldName.
   *
   * @param aFieldName the fieldName to set (not null)
   */
  public void setFieldName(String aFieldName) {
    fieldName = aFieldName;
  }

  /**
   * Gets the fieldCaption.
   *
   * @return the fieldCaption (not null)
   */
  public String getFieldCaption() {
    return fieldCaption;
  }

  /**
   * Sets the fieldCaption.
   *
   * @param aFieldCaption the fieldCaption to set (not null)
   */
  public void setFieldCaption(String aFieldCaption) {
    fieldCaption = aFieldCaption;
  }

  /**
   * Gets the isResolved.
   *
   * @return the isResolved (not null)
   */
  public boolean isResolved() {
    return isResolved;
  }

  /**
   * Sets the isResolved.
   *
   * @param aIsResolved the isResolved to set (not null)
   */
  public void setResolved(boolean aIsResolved) {
    isResolved = aIsResolved;
  }
}
