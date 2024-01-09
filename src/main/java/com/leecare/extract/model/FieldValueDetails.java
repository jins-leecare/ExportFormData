/*
 * FieldValueDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This is used for a FieldValueDetails.
 *
 * @author jjoy
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

  /** Constructs a FieldValueDetails. */
  public FieldValueDetails() {}

  /**
   * Constructs a FieldValueDetails.
   *
   * @param aRecordID recordID
   * @param aFieldValue fieldValue
   * @param aDateCreated dateCreated
   * @param aCreatedBy createdBy
   * @param aBatchID batchID
   * @param aSchemaID schemaID
   * @param aValueDate valueDate
   * @param aValueNumber valueNumber
   * @param aValueBit valueBit
   * @param aFieldName fieldName
   * @param aFieldCaption fieldCaption
   */
  public FieldValueDetails(
      Integer aRecordID,
      String aFieldValue,
      Date aDateCreated,
      String aCreatedBy,
      Integer aBatchID,
      Integer aSchemaID,
      Date aValueDate,
      Double aValueNumber,
      String aValueBit,
      String aFieldName,
      String aFieldCaption) {
    recordID = aRecordID;
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
   * @param aRecordID
   * @param aFieldValue
   */
  public FieldValueDetails(Integer aRecordID, String aFieldValue) {
    super();
    recordID = aRecordID;
    fieldValue = aFieldValue;
  }

  /**
   * Constructs a FieldValueDetails.
   *
   * @param aRecordID recordID
   * @param aFieldValue fieldValue
   * @param aDateCreated dateCreated
   * @param aValueDate valueDate
   */
  public FieldValueDetails(
      Integer aRecordID, String aFieldValue, Date aDateCreated, Date aValueDate) {
    this.recordID = aRecordID;
    this.fieldValue = aFieldValue;
    this.dateCreated = aDateCreated;
    this.valueDate = aValueDate;
  }

  /** @return createdBy */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the createdBy
   *
   * @param createdBy
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /** @return the recordID */
  public Integer getRecordID() {
    return recordID;
  }
  /**
   * Sets the recordID.
   *
   * @param aRecordID the recordID to set
   */
  public void setRecordID(Integer aRecordID) {
    recordID = aRecordID;
  }
  /** @return the fieldValue */
  public String getFieldValue() {
    return fieldValue;
  }
  /**
   * Sets the fieldValue.
   *
   * @param aFieldValue the fieldValue to set
   */
  public void setFieldValue(String aFieldValue) {
    fieldValue = aFieldValue;
  }
  /** @return the dateCreated */
  public Date getDateCreated() {
    return dateCreated;
  }
  /**
   * Sets the dateCreated.
   *
   * @param aDateCreated the dateCreated to set
   */
  public void setDateCreated(Date aDateCreated) {
    dateCreated = aDateCreated;
  }
  /** @return the batchID */
  public Integer getBatchID() {
    return batchID;
  }
  /**
   * Sets the batchID.
   *
   * @param aBatchID the batchID to set
   */
  public void setBatchID(Integer aBatchID) {
    batchID = aBatchID;
  }
  /** @return the schemaID */
  public Integer getSchemaID() {
    return schemaID;
  }
  /**
   * Sets the schemaID.
   *
   * @param aSchemaID the schemaID to set
   */
  public void setSchemaID(Integer aSchemaID) {
    schemaID = aSchemaID;
  }
  /** @return the valueDate */
  public Date getValueDate() {
    return valueDate;
  }
  /**
   * Sets the valueDate.
   *
   * @param aValueDate the valueDate to set
   */
  public void setValueDate(Date aValueDate) {
    valueDate = aValueDate;
  }
  /** @return the valueNumber */
  public Double getValueNumber() {
    return valueNumber;
  }
  /**
   * Sets the valueNumber.
   *
   * @param aValueNumber the valueNumber to set
   */
  public void setValueNumber(Double aValueNumber) {
    valueNumber = aValueNumber;
  }
  /** @return valueBit */
  public String getValueBit() {
    return valueBit;
  }
  /** @param aValueBit */
  public void setValueBit(String aValueBit) {
    valueBit = aValueBit;
  }
  /** @return the fieldName */
  public String getFieldName() {
    return fieldName;
  }
  /**
   * Sets the fieldName.
   *
   * @param aFieldName the fieldName to set
   */
  public void setFieldName(String aFieldName) {
    fieldName = aFieldName;
  }
  /** @return the fieldCaption */
  public String getFieldCaption() {
    return fieldCaption;
  }
  /**
   * Sets the fieldCaption.
   *
   * @param aFieldCaption the fieldCaption to set
   */
  public void setFieldCaption(String aFieldCaption) {
    fieldCaption = aFieldCaption;
  }
}
