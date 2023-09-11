package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;

public class FieldValueDetails implements Serializable {
    private Integer recordID;
    private String fieldValue;
    private Date dateCreated;
    private Integer batchID;
    private Integer schemaID;
    private Date valueDate;
    private Double valueNumber;
    private String valueBit;
    public FieldValueDetails() {
    }
    /**
     * Constructs a FieldValueDetails.
     *
     * @param aRecordID
     * @param aFieldValue
     * @param aDateCreated
     * @param aBatchID
     * @param aSchemaID
     * @param aValueDate
     * @param aValueNumber
     * @param aValueBit
     */
    public FieldValueDetails(
            Integer aRecordID,
            String aFieldValue,
            Date aDateCreated,
            Integer aBatchID,
            Integer aSchemaID,
            Date aValueDate,
            Double aValueNumber,
            String aValueBit) {
        super();
        recordID = aRecordID;
        fieldValue = aFieldValue;
        dateCreated = aDateCreated;
        batchID = aBatchID;
        schemaID = aSchemaID;
        valueDate = aValueDate;
        valueNumber = aValueNumber;
        valueBit = aValueBit;
    }

    public FieldValueDetails(Integer recordID, String fieldValue, Date dateCreated, Date valueDate) {
        this.recordID = recordID;
        this.fieldValue = fieldValue;
        this.dateCreated = dateCreated;
        this.valueDate = valueDate;
    }

    /**
     * @return the recordID
     */
    public Integer getRecordID() {
        return recordID;}
    /**
     * Sets the recordID.
     *
     * @param aRecordID the recordID to set
     */
    public void setRecordID(Integer aRecordID) {
        recordID = aRecordID;}
    /**
     * @return the fieldValue
     */
    public String getFieldValue() {
        return fieldValue;}
    /**
     * Sets the fieldValue.
     *
     * @param aFieldValue the fieldValue to set
     */
    public void setFieldValue(String aFieldValue) {
        fieldValue = aFieldValue;}
    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;}
    /**
     * Sets the dateCreated.
     *
     * @param aDateCreated the dateCreated to set
     */
    public void setDateCreated(Date aDateCreated) {
        dateCreated = aDateCreated;}
    /**
     * @return the batchID
     */
    public Integer getBatchID() {
        return batchID;}
    /**
     * Sets the batchID.
     *
     * @param aBatchID the batchID to set
     */
    public void setBatchID(Integer aBatchID) {
        batchID = aBatchID;}
    /**
     * @return the schemaID
     */
    public Integer getSchemaID() {
        return schemaID;}
    /**
     * Sets the schemaID.
     *
     * @param aSchemaID the schemaID to set
     */
    public void setSchemaID(Integer aSchemaID) {
        schemaID = aSchemaID;}
    /**
     * @return the valueDate
     */
    public Date getValueDate() {
        return valueDate;}
    /**
     * Sets the valueDate.
     *
     * @param aValueDate the valueDate to set
     */
    public void setValueDate(Date aValueDate) {
        valueDate = aValueDate;}
    /**
     * @return the valueNumber
     */
    public Double getValueNumber() {
        return valueNumber;}
    /**
     * Sets the valueNumber.
     *
     * @param aValueNumber the valueNumber to set
     */
    public void setValueNumber(Double aValueNumber) {
        valueNumber = aValueNumber;}

    public String getValueBit() {
        return valueBit;
    }

    public void setValueBit(String valueBit) {
        this.valueBit = valueBit;
    }
}
