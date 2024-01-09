/*
 * FieldValue.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * This is used for a FieldValue.
 *
 * @author jjoy
 */
public class FieldValue implements Serializable {
  private String value;
  private String fieldName;
  private String fieldCaption;

  /** Constructs a FieldValue. */
  public FieldValue() {}

  /**
   * Constructs a FieldValue.
   *
   * @param aValue value
   * @param aFieldName fieldName
   * @param aFieldCaption field caption
   */
  @JsonCreator
  public FieldValue(
      @JsonProperty("value") String aValue,
      @JsonProperty("fieldName") String aFieldName,
      @JsonProperty("fieldCaption") String aFieldCaption) {
    this.value = aValue;
    this.fieldName = aFieldName;
    this.fieldCaption = aFieldCaption;
  }

  /**
   * Constructs a FieldValue.
   *
   * @param value
   */
  public FieldValue(String value) {
    this.value = value;
  }

  /** @return the value */
  public String getValue() {
    return value;
  }
  /**
   * Sets the value.
   *
   * @param aValue the value to set
   */
  public void setValue(String aValue) {
    value = aValue;
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
