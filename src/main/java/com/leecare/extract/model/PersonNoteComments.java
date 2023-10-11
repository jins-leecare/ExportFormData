/*
 * PersonNoteComments.java
 *
 * Copyright © 2020 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class PersonNoteComments implements Serializable {
  private int id;
  private int noteId;
  private String createdOnForReport;
  private String comment;
  private int createdUserId;
  private String createdUserName;
  private boolean readBefore;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNoteId() {
    return noteId;
  }

  public void setNoteId(int noteId) {
    this.noteId = noteId;
  }

  public void setCreatedOnForReport(String createdOnForReport) {
    this.createdOnForReport = createdOnForReport;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getCreatedUserId() {
    return createdUserId;
  }

  public void setCreatedUserId(int createdUserId) {
    this.createdUserId = createdUserId;
  }

  public String getCreatedUserName() {
    return createdUserName;
  }

  public void setCreatedUserName(String createdUserName) {
    this.createdUserName = createdUserName;
  }

  public String getCreatedOnForReport() {
    return createdOnForReport;
  }

  public boolean isReadBefore() {
    return readBefore;
  }

  public void setReadBefore(boolean readBefore) {
    this.readBefore = readBefore;
  }
}
