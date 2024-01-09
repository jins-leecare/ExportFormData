/*
 * PersonNoteComments.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;

/**
 * This is used for a PersonNoteComments.
 *
 * @author jjoy
 */
public class PersonNoteComments implements Serializable {
  private int id;
  private int noteId;
  private String createdOnForReport;
  private String comment;
  private int createdUserId;
  private String createdUserName;
  private boolean readBefore;
  /** @return the id */
  public int getId() {
    return id;
  }
  /**
   * Sets the id.
   *
   * @param aId the id to set
   */
  public void setId(int aId) {
    id = aId;
  }
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
  /** @return the createdOnForReport */
  public String getCreatedOnForReport() {
    return createdOnForReport;
  }
  /**
   * Sets the createdOnForReport.
   *
   * @param aCreatedOnForReport the createdOnForReport to set
   */
  public void setCreatedOnForReport(String aCreatedOnForReport) {
    createdOnForReport = aCreatedOnForReport;
  }
  /** @return the comment */
  public String getComment() {
    return comment;
  }
  /**
   * Sets the comment.
   *
   * @param aComment the comment to set
   */
  public void setComment(String aComment) {
    comment = aComment;
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
  /** @return the createdUserName */
  public String getCreatedUserName() {
    return createdUserName;
  }
  /**
   * Sets the createdUserName.
   *
   * @param aCreatedUserName the createdUserName to set
   */
  public void setCreatedUserName(String aCreatedUserName) {
    createdUserName = aCreatedUserName;
  }
  /** @return the readBefore */
  public boolean isReadBefore() {
    return readBefore;
  }
  /**
   * Sets the readBefore.
   *
   * @param aReadBefore the readBefore to set
   */
  public void setReadBefore(boolean aReadBefore) {
    readBefore = aReadBefore;
  }
}
