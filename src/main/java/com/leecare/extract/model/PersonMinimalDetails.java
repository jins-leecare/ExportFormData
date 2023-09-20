/*
 * PersonMinimalDetails.java
 *
 * Copyright © 2022 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;


import java.io.Serializable;
import java.util.Date;


public class PersonMinimalDetails
    implements Serializable, Comparable<PersonMinimalDetails> {
  private int personId;
  private String firstName;
  private String lastName;
  private String preferredName;
  private String reservedBedName;
  private Object admissionStatus;
  private String occupiedBedName;
  private Date photoUpdatedOn;
  private boolean archieved;
  private String IHINUmber;
  private String medicareNumber;
  private String dvanumber;
  private Integer facilityId;
  private Object acfiReAppraisalType;
  private Object acfiAppraisalType;
  private Date applicableAdmissionDate;
  private Date createdDate;
  private Integer alertId;
  private Object admissionType;
  private String contactDetails;
  private String suggestString;
  private String suggestId;
  private String fullName;
  public int getPersonId() {
    return personId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPreferredName() {
    return preferredName;
  }

  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }

  public String getReservedBedName() {
    return reservedBedName;
  }

  public void setReservedBedName(String reservedBedName) {
    this.reservedBedName = reservedBedName;
  }

  public Object getAdmissionStatus() {
    return admissionStatus;
  }

  public void setAdmissionStatus(Object admissionStatus) {
    this.admissionStatus = admissionStatus;
  }

  public String getOccupiedBedName() {
    return occupiedBedName;
  }

  public void setOccupiedBedName(String occupiedBedName) {
    this.occupiedBedName = occupiedBedName;
  }

  public Date getPhotoUpdatedOn() {
    return photoUpdatedOn;
  }

  public void setPhotoUpdatedOn(Date photoUpdatedOn) {
    this.photoUpdatedOn = photoUpdatedOn;
  }

  public boolean isArchieved() {
    return archieved;
  }

  public void setArchieved(boolean archieved) {
    this.archieved = archieved;
  }

  public String getIHINUmber() {
    return IHINUmber;
  }

  public void setIHINUmber(String IHINUmber) {
    this.IHINUmber = IHINUmber;
  }

  public String getMedicareNumber() {
    return medicareNumber;
  }

  public void setMedicareNumber(String medicareNumber) {
    this.medicareNumber = medicareNumber;
  }

  public String getDvanumber() {
    return dvanumber;
  }

  public void setDvanumber(String dvanumber) {
    this.dvanumber = dvanumber;
  }

  public Integer getFacilityId() {
    return facilityId;
  }

  public void setFacilityId(Integer facilityId) {
    this.facilityId = facilityId;
  }

  public Object getAcfiReAppraisalType() {
    return acfiReAppraisalType;
  }

  public void setAcfiReAppraisalType(Object acfiReAppraisalType) {
    this.acfiReAppraisalType = acfiReAppraisalType;
  }

  public Object getAcfiAppraisalType() {
    return acfiAppraisalType;
  }

  public void setAcfiAppraisalType(Object acfiAppraisalType) {
    this.acfiAppraisalType = acfiAppraisalType;
  }

  public Date getApplicableAdmissionDate() {
    return applicableAdmissionDate;
  }

  public void setApplicableAdmissionDate(Date applicableAdmissionDate) {
    this.applicableAdmissionDate = applicableAdmissionDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Integer getAlertId() {
    return alertId;
  }

  public void setAlertId(Integer alertId) {
    this.alertId = alertId;
  }

  public Object getAdmissionType() {
    return admissionType;
  }

  public void setAdmissionType(Object admissionType) {
    this.admissionType = admissionType;
  }

  public String getContactDetails() {
    return contactDetails;
  }

  public void setContactDetails(String contactDetails) {
    this.contactDetails = contactDetails;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PersonMinimalDetails) {
      return this.personId == ((PersonMinimalDetails) obj).personId;
    }
    return super.equals(obj);
  }

  public String getFullName() {
    return (this.getFirstName() == null ? "" : this.getFirstName().trim())
        + " "
        + (this.getLastName() == null ? "" : this.getLastName().toUpperCase().trim());
  }

  public String getSuggestId() {
    return String.valueOf(this.getPersonId());
  }

  public String getSuggestString() {
    return getFullName() + " " + getPreferredName();
  }

  @Override
  public int compareTo(PersonMinimalDetails o) {
    //
    // PLATM-905 - Avoid residents with same surname gets removed in Java List collections.
    // Also resolved issues occurred in SPPRT-5253

    int result = this.getLastName().compareToIgnoreCase(o.getLastName());
    if (result == 0) {
      return this.getFirstName().compareToIgnoreCase(o.getFirstName());
    }

    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PersonMinimalDetails [personId=");
    builder.append(personId);
    builder.append(", firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append(", preferredName=");
    builder.append(preferredName);
    builder.append(", reservedBedName=");
    builder.append(reservedBedName);
    builder.append(", applicableAdmissionDate=");
    builder.append(applicableAdmissionDate);
    builder.append(", admissionStatus=");
    builder.append(admissionStatus);
    builder.append(", occupiedBedName=");
    builder.append(occupiedBedName);
    builder.append(", photoUpdatedOn=");
    builder.append(photoUpdatedOn);
    builder.append(", archieved=");
    builder.append(archieved);
    builder.append(", IHINUmber=");
    builder.append(IHINUmber);
    builder.append(", medicarenumber=");
    builder.append(medicareNumber);
    builder.append(", dvanumber=");
    builder.append(dvanumber);
    builder.append(", facilityId=");
    builder.append(facilityId);
    builder.append(", acfiReAppraisalType=");
    builder.append(acfiReAppraisalType);
    builder.append(", createdDate=");
    builder.append(createdDate);
    builder.append(", acfiAppraisalType=");
    builder.append(acfiAppraisalType);
    builder.append("]");
    return builder.toString();
  }
}
