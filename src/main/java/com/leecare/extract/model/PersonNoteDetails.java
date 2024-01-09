/*
 * PersonNoteDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.List;

/**
 * This is used for a PersonNoteDetails.
 *
 * @author jjoy
 */
public class PersonNoteDetails extends PersonNoteBase {
  private List<PersonNoteComments> comments;
  private List<Object> alertDetails;
  private List<Object> followupAlert;
  private String categoryString;
  private String categoryStringForReports;
  private String createdUserName;
  private PersonMinimalDetails personDetails;
  private Object status;
  private String savedInErrorComment;
  private String savedInErrorUserName;
  private Object savedInErrorDate;
  private boolean read;
  private boolean systemGenerated;
  private String admittedTo;
  private String admittedToForReports;
  private String residentName;
  private String statusString;
  private String commentsString;
  private String subReportDirectory;
  /** @return the comments */
  public List<PersonNoteComments> getComments() {
    return comments;
  }
  /**
   * Sets the comments.
   *
   * @param aComments the comments to set
   */
  public void setComments(List<PersonNoteComments> aComments) {
    comments = aComments;
  }
  /** @return the alertDetails */
  public List<Object> getAlertDetails() {
    return alertDetails;
  }
  /**
   * Sets the alertDetails.
   *
   * @param aAlertDetails the alertDetails to set
   */
  public void setAlertDetails(List<Object> aAlertDetails) {
    alertDetails = aAlertDetails;
  }
  /** @return the followupAlert */
  public List<Object> getFollowupAlert() {
    return followupAlert;
  }
  /**
   * Sets the followupAlert.
   *
   * @param aFollowupAlert the followupAlert to set
   */
  public void setFollowupAlert(List<Object> aFollowupAlert) {
    followupAlert = aFollowupAlert;
  }
  /** @return the categoryString */
  public String getCategoryString() {
    return categoryString;
  }
  /**
   * Sets the categoryString.
   *
   * @param aCategoryString the categoryString to set
   */
  public void setCategoryString(String aCategoryString) {
    categoryString = aCategoryString;
  }
  /** @return the categoryStringForReports */
  public String getCategoryStringForReports() {
    return categoryStringForReports;
  }
  /**
   * Sets the categoryStringForReports.
   *
   * @param aCategoryStringForReports the categoryStringForReports to set
   */
  public void setCategoryStringForReports(String aCategoryStringForReports) {
    categoryStringForReports = aCategoryStringForReports;
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
  /** @return the personDetails */
  public PersonMinimalDetails getPersonDetails() {
    return personDetails;
  }
  /**
   * Sets the personDetails.
   *
   * @param aPersonDetails the personDetails to set
   */
  public void setPersonDetails(PersonMinimalDetails aPersonDetails) {
    personDetails = aPersonDetails;
  }
  /** @return the status */
  public Object getStatus() {
    return status;
  }
  /**
   * Sets the status.
   *
   * @param aStatus the status to set
   */
  public void setStatus(Object aStatus) {
    status = aStatus;
  }
  /** @return the savedInErrorComment */
  public String getSavedInErrorComment() {
    return savedInErrorComment;
  }
  /**
   * Sets the savedInErrorComment.
   *
   * @param aSavedInErrorComment the savedInErrorComment to set
   */
  public void setSavedInErrorComment(String aSavedInErrorComment) {
    savedInErrorComment = aSavedInErrorComment;
  }
  /** @return the savedInErrorUserName */
  public String getSavedInErrorUserName() {
    return savedInErrorUserName;
  }
  /**
   * Sets the savedInErrorUserName.
   *
   * @param aSavedInErrorUserName the savedInErrorUserName to set
   */
  public void setSavedInErrorUserName(String aSavedInErrorUserName) {
    savedInErrorUserName = aSavedInErrorUserName;
  }
  /** @return the savedInErrorDate */
  public Object getSavedInErrorDate() {
    return savedInErrorDate;
  }
  /**
   * Sets the savedInErrorDate.
   *
   * @param aSavedInErrorDate the savedInErrorDate to set
   */
  public void setSavedInErrorDate(Object aSavedInErrorDate) {
    savedInErrorDate = aSavedInErrorDate;
  }
  /** @return the read */
  public boolean isRead() {
    return read;
  }
  /**
   * Sets the read.
   *
   * @param aRead the read to set
   */
  public void setRead(boolean aRead) {
    read = aRead;
  }
  /** @return the systemGenerated */
  public boolean isSystemGenerated() {
    return systemGenerated;
  }
  /**
   * Sets the systemGenerated.
   *
   * @param aSystemGenerated the systemGenerated to set
   */
  public void setSystemGenerated(boolean aSystemGenerated) {
    systemGenerated = aSystemGenerated;
  }
  /** @return the admittedTo */
  public String getAdmittedTo() {
    return admittedTo;
  }
  /**
   * Sets the admittedTo.
   *
   * @param aAdmittedTo the admittedTo to set
   */
  public void setAdmittedTo(String aAdmittedTo) {
    admittedTo = aAdmittedTo;
  }
  /** @return the admittedToForReports */
  public String getAdmittedToForReports() {
    return admittedToForReports;
  }
  /**
   * Sets the admittedToForReports.
   *
   * @param aAdmittedToForReports the admittedToForReports to set
   */
  public void setAdmittedToForReports(String aAdmittedToForReports) {
    admittedToForReports = aAdmittedToForReports;
  }
  /** @return the residentName */
  public String getResidentName() {
    return residentName;
  }
  /**
   * Sets the residentName.
   *
   * @param aResidentName the residentName to set
   */
  public void setResidentName(String aResidentName) {
    residentName = aResidentName;
  }
  /** @return the statusString */
  public String getStatusString() {
    return statusString;
  }
  /**
   * Sets the statusString.
   *
   * @param aStatusString the statusString to set
   */
  public void setStatusString(String aStatusString) {
    statusString = aStatusString;
  }
  /** @return the commentsString */
  public String getCommentsString() {
    return commentsString;
  }
  /**
   * Sets the commentsString.
   *
   * @param aCommentsString the commentsString to set
   */
  public void setCommentsString(String aCommentsString) {
    commentsString = aCommentsString;
  }
  /** @return the subReportDirectory */
  public String getSubReportDirectory() {
    return subReportDirectory;
  }
  /**
   * Sets the subReportDirectory.
   *
   * @param aSubReportDirectory the subReportDirectory to set
   */
  public void setSubReportDirectory(String aSubReportDirectory) {
    subReportDirectory = aSubReportDirectory;
  }
}
