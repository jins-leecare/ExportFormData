/*
 * PersonNoteDetails.java
 *
 * Copyright © 2019 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.List;

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
  private String subReportDirectory; // only for reporting purposes.

  public List<PersonNoteComments> getComments() {
    return comments;
  }

  public void setComments(List<PersonNoteComments> comments) {
    this.comments = comments;
  }

  public List<Object> getAlertDetails() {
    return alertDetails;
  }

  public void setAlertDetails(List<Object> alertDetails) {
    this.alertDetails = alertDetails;
  }

  public List<Object> getFollowupAlert() {
    return followupAlert;
  }

  public void setFollowupAlert(List<Object> followupAlert) {
    this.followupAlert = followupAlert;
  }

  public String getCategoryString() {
    return categoryString;
  }

  public void setCategoryString(String categoryString) {
    this.categoryString = categoryString;
  }

  public String getCategoryStringForReports() {
    return categoryStringForReports;
  }

  public void setCategoryStringForReports(String categoryStringForReports) {
    this.categoryStringForReports = categoryStringForReports;
  }

  public String getCreatedUserName() {
    return createdUserName;
  }

  public void setCreatedUserName(String createdUserName) {
    this.createdUserName = createdUserName;
  }

  public PersonMinimalDetails getPersonDetails() {
    return personDetails;
  }

  public void setPersonDetails(PersonMinimalDetails personDetails) {
    this.personDetails = personDetails;
  }

  public Object getStatus() {
    return status;
  }

  public void setStatus(Object status) {
    this.status = status;
  }

  public String getSavedInErrorComment() {
    return savedInErrorComment;
  }

  public void setSavedInErrorComment(String savedInErrorComment) {
    this.savedInErrorComment = savedInErrorComment;
  }

  public String getSavedInErrorUserName() {
    return savedInErrorUserName;
  }

  public void setSavedInErrorUserName(String savedInErrorUserName) {
    this.savedInErrorUserName = savedInErrorUserName;
  }

  public Object getSavedInErrorDate() {
    return savedInErrorDate;
  }

  public void setSavedInErrorDate(Object savedInErrorDate) {
    this.savedInErrorDate = savedInErrorDate;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public boolean isSystemGenerated() {
    return systemGenerated;
  }

  public void setSystemGenerated(boolean systemGenerated) {
    this.systemGenerated = systemGenerated;
  }

  public String getAdmittedTo() {
    return admittedTo;
  }

  public void setAdmittedTo(String admittedTo) {
    this.admittedTo = admittedTo;
  }

  public String getAdmittedToForReports() {
    return admittedToForReports;
  }

  public void setAdmittedToForReports(String admittedToForReports) {
    this.admittedToForReports = admittedToForReports;
  }

  public String getResidentName() {
    return residentName;
  }

  public void setResidentName(String residentName) {
    this.residentName = residentName;
  }

  public String getStatusString() {
    return statusString;
  }

  public void setStatusString(String statusString) {
    this.statusString = statusString;
  }

  public String getCommentsString() {
    return commentsString;
  }

  public void setCommentsString(String commentsString) {
    this.commentsString = commentsString;
  }

  public String getSubReportDirectory() {
    return subReportDirectory;
  }

  public void setSubReportDirectory(String subReportDirectory) {
    this.subReportDirectory = subReportDirectory;
  }
}
