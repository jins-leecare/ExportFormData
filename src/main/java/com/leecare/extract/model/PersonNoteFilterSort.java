/*
 * PersonNoteFilterSort.java
 *
 * 16/10/2012 Yoseph Phillips - Changed Values as part of fix for PLATM-621
 * 02/05/2014 Sara Soliman - Added room and changed location name [PLATM-862]
 *
 * Copyright 2014 - Leecare
 */
package com.leecare.extract.model;


import java.io.Serializable;

public enum PersonNoteFilterSort implements Serializable {
  CREATED_DATE("DateCreated"),
  LOCATION("Location_Name"),
  RESIDENT_NAME("Person_Name"),
  ROOM("Room_Name");

  private String fieldName;

  private PersonNoteFilterSort(String aFieldName) {
    fieldName = aFieldName;
  }

  public String getFieldName() {
    return fieldName;
  }
}
