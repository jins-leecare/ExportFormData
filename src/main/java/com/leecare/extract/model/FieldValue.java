package com.leecare.extract.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FieldValue implements Serializable {
    private String value;
    private String fieldName;
    private String fieldCaption;

    public FieldValue() {
    }

    @JsonCreator
    public FieldValue(
            @JsonProperty("value") String value,
            @JsonProperty("fieldName") String fieldName,
            @JsonProperty("fieldCaption") String fieldCaption) {
        this.value = value;
        this.fieldName = fieldName;
        this.fieldCaption = fieldCaption;
    }

    public FieldValue(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;}
    /**
     * Sets the value.
     *
     * @param aValue the value to set
     */
    public void setValue(String aValue) {
        value = aValue;}
    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;}
    /**
     * Sets the fieldName.
     *
     * @param aFieldName the fieldName to set
     */
    public void setFieldName(String aFieldName) {
        fieldName = aFieldName;}
    /**
     * @return the fieldCaption
     */
    public String getFieldCaption() {
        return fieldCaption;}
    /**
     * Sets the fieldCaption.
     *
     * @param aFieldCaption the fieldCaption to set
     */
    public void setFieldCaption(String aFieldCaption) {
        fieldCaption = aFieldCaption;}
}
