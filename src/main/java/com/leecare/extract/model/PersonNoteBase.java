/*
 * PersonNoteBase.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * This is used for a PersonNoteBase.
 *
 * @author jjoy
 */
public class PersonNoteBase implements Serializable {
  private int noteId;
  private Integer personId;
  private int createdUserId;
  private Date createdOn;
  private Date archivedOn;
  private String noteText;
  private Set<Integer> categoryIds;
  private Object noteStatus;
  private Object associatedAlert;
  private Integer recordId;
  /** @return the noteId */
  public int getNoteId() {
    return noteId;
  }
  /**
   * Sets the noteId.
   *
   * @param aNoteId the noteId to set
   */
  public void setNoteId(int aNoteId) {
    noteId = aNoteId;
  }
  /** @return the personId */
  public Integer getPersonId() {
    return personId;
  }
  /**
   * Sets the personId.
   *
   * @param aPersonId the personId to set
   */
  public void setPersonId(Integer aPersonId) {
    personId = aPersonId;
  }
  /** @return the createdUserId */
  public int getCreatedUserId() {
    return createdUserId;
  }
  /**
   * Sets the createdUserId.
   *
   * @param aCreatedUserId the createdUserId to set
   */
  public void setCreatedUserId(int aCreatedUserId) {
    createdUserId = aCreatedUserId;
  }
  /** @return the createdOn */
  public Date getCreatedOn() {
    return createdOn;
  }
  /**
   * Sets the createdOn.
   *
   * @param aCreatedOn the createdOn to set
   */
  public void setCreatedOn(Date aCreatedOn) {
    createdOn = aCreatedOn;
  }
  /** @return the archivedOn */
  public Date getArchivedOn() {
    return archivedOn;
  }
  /**
   * Sets the archivedOn.
   *
   * @param aArchivedOn the archivedOn to set
   */
  public void setArchivedOn(Date aArchivedOn) {
    archivedOn = aArchivedOn;
  }
  /** @return the noteText */
  public String getNoteText() {
    return noteText;
  }
  /**
   * Sets the noteText.
   *
   * @param aNoteText the noteText to set
   */
  public void setNoteText(String aNoteText) {
    noteText = aNoteText;
  }
  /** @return the categoryIds */
  public Set<Integer> getCategoryIds() {
    return categoryIds;
  }
  /**
   * Sets the categoryIds.
   *
   * @param aCategoryIds the categoryIds to set
   */
  public void setCategoryIds(Set<Integer> aCategoryIds) {
    categoryIds = aCategoryIds;
  }
  /** @return the noteStatus */
  public Object getNoteStatus() {
    return noteStatus;
  }
  /**
   * Sets the noteStatus.
   *
   * @param aNoteStatus the noteStatus to set
   */
  public void setNoteStatus(Object aNoteStatus) {
    noteStatus = aNoteStatus;
  }
  /** @return the associatedAlert */
  public Object getAssociatedAlert() {
    return associatedAlert;
  }
  /**
   * Sets the associatedAlert.
   *
   * @param aAssociatedAlert the associatedAlert to set
   */
  public void setAssociatedAlert(Object aAssociatedAlert) {
    associatedAlert = aAssociatedAlert;
  }
  /** @return the recordId */
  public Integer getRecordId() {
    return recordId;
  }
  /**
   * Sets the recordId.
   *
   * @param aRecordId the recordId to set
   */
  public void setRecordId(Integer aRecordId) {
    recordId = aRecordId;
  }
}
