/*
 * PersonNoteFilterSort.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;

/**
 * This is used for a PersonNoteFilterSort.
 *
 * @author jjoy
 */
public enum PersonNoteFilterSort implements Serializable {
  CREATED_DATE("DateCreated"),
  LOCATION("Location_Name"),
  RESIDENT_NAME("Person_Name"),
  ROOM("Room_Name");

  private String fieldName;

  private PersonNoteFilterSort(String aFieldName) {
    fieldName = aFieldName;
  }

  /** @return fieldName */
  public String getFieldName() {
    return fieldName;
  }
}
