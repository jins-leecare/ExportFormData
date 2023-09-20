/*
 * PersonNoteBase.java
 *
 * Copyright © 2019 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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

  public int getNoteId() {
    return noteId;
  }

  public void setNoteId(int noteId) {
    this.noteId = noteId;
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  public int getCreatedUserId() {
    return createdUserId;
  }

  public void setCreatedUserId(int createdUserId) {
    this.createdUserId = createdUserId;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date aDate) {
    assert aDate != null;
    createdOn = aDate;
  }

  public Date getArchivedOn() {
    return archivedOn;
  }

  public void setArchivedOn(Date aDate) {
    archivedOn = aDate;
  }

  public String getNoteText() {
    return noteText;
  }

  public void setNoteText(String noteText) {
    this.noteText = noteText;
  }

  public Set<Integer> getCategories() {
    return categoryIds;
  }

  public void setCategories(Set<Integer> categories) {
    this.categoryIds = categories;
  }

  public Object getNoteStatus() {
    return noteStatus;
  }

  public void setNoteStatus(Object noteStatus) {
    this.noteStatus = noteStatus;
  }

  public Object getAssociatedAlert() {
    return associatedAlert;
  }

  public void setAssociatedAlert(Object associatedAlert) {
    this.associatedAlert = associatedAlert;
  }

  public Integer getRecordId() {
    return recordId;
  }

  public void setRecordId(Integer recordId) {
    this.recordId = recordId;
  }
}
