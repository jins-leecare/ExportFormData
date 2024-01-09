/*
 * PersonMinimalDetails.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This is used for a PersonMinimalDetails.
 *
 * @author jjoy
 */
public class PersonMinimalDetails implements Serializable, Comparable<PersonMinimalDetails> {
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

  /** @return the personId */
  public int getPersonId() {
    return personId;
  }

  /**
   * Sets the personId.
   *
   * @param aPersonId the personId to set
   */
  public void setPersonId(int aPersonId) {
    personId = aPersonId;
  }

  /** @return the firstName */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the firstName.
   *
   * @param aFirstName the firstName to set
   */
  public void setFirstName(String aFirstName) {
    firstName = aFirstName;
  }

  /** @return the lastName */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the lastName.
   *
   * @param aLastName the lastName to set
   */
  public void setLastName(String aLastName) {
    lastName = aLastName;
  }

  /** @return the preferredName */
  public String getPreferredName() {
    return preferredName;
  }

  /**
   * Sets the preferredName.
   *
   * @param aPreferredName the preferredName to set
   */
  public void setPreferredName(String aPreferredName) {
    preferredName = aPreferredName;
  }

  /** @return the reservedBedName */
  public String getReservedBedName() {
    return reservedBedName;
  }

  /**
   * Sets the reservedBedName.
   *
   * @param aReservedBedName the reservedBedName to set
   */
  public void setReservedBedName(String aReservedBedName) {
    reservedBedName = aReservedBedName;
  }

  /** @return the admissionStatus */
  public Object getAdmissionStatus() {
    return admissionStatus;
  }

  /**
   * Sets the admissionStatus.
   *
   * @param aAdmissionStatus the admissionStatus to set
   */
  public void setAdmissionStatus(Object aAdmissionStatus) {
    admissionStatus = aAdmissionStatus;
  }

  /** @return the occupiedBedName */
  public String getOccupiedBedName() {
    return occupiedBedName;
  }

  /**
   * Sets the occupiedBedName.
   *
   * @param aOccupiedBedName the occupiedBedName to set
   */
  public void setOccupiedBedName(String aOccupiedBedName) {
    occupiedBedName = aOccupiedBedName;
  }

  /** @return the photoUpdatedOn */
  public Date getPhotoUpdatedOn() {
    return photoUpdatedOn;
  }

  /**
   * Sets the photoUpdatedOn.
   *
   * @param aPhotoUpdatedOn the photoUpdatedOn to set
   */
  public void setPhotoUpdatedOn(Date aPhotoUpdatedOn) {
    photoUpdatedOn = aPhotoUpdatedOn;
  }

  /** @return the archieved */
  public boolean isArchieved() {
    return archieved;
  }

  /**
   * Sets the archieved.
   *
   * @param aArchieved the archieved to set
   */
  public void setArchieved(boolean aArchieved) {
    archieved = aArchieved;
  }

  /** @return the iHINUmber */
  public String getIHINUmber() {
    return IHINUmber;
  }

  /**
   * Sets the iHINUmber.
   *
   * @param aIHINUmber the iHINUmber to set
   */
  public void setIHINUmber(String aIHINUmber) {
    IHINUmber = aIHINUmber;
  }

  /** @return the medicareNumber */
  public String getMedicareNumber() {
    return medicareNumber;
  }

  /**
   * Sets the medicareNumber.
   *
   * @param aMedicareNumber the medicareNumber to set
   */
  public void setMedicareNumber(String aMedicareNumber) {
    medicareNumber = aMedicareNumber;
  }

  /** @return the dvanumber */
  public String getDvanumber() {
    return dvanumber;
  }

  /**
   * Sets the dvanumber.
   *
   * @param aDvanumber the dvanumber to set
   */
  public void setDvanumber(String aDvanumber) {
    dvanumber = aDvanumber;
  }

  /** @return the facilityId */
  public Integer getFacilityId() {
    return facilityId;
  }

  /**
   * Sets the facilityId.
   *
   * @param aFacilityId the facilityId to set
   */
  public void setFacilityId(Integer aFacilityId) {
    facilityId = aFacilityId;
  }

  /** @return the acfiReAppraisalType */
  public Object getAcfiReAppraisalType() {
    return acfiReAppraisalType;
  }

  /**
   * Sets the acfiReAppraisalType.
   *
   * @param aAcfiReAppraisalType the acfiReAppraisalType to set
   */
  public void setAcfiReAppraisalType(Object aAcfiReAppraisalType) {
    acfiReAppraisalType = aAcfiReAppraisalType;
  }

  /** @return the acfiAppraisalType */
  public Object getAcfiAppraisalType() {
    return acfiAppraisalType;
  }

  /**
   * Sets the acfiAppraisalType.
   *
   * @param aAcfiAppraisalType the acfiAppraisalType to set
   */
  public void setAcfiAppraisalType(Object aAcfiAppraisalType) {
    acfiAppraisalType = aAcfiAppraisalType;
  }

  /** @return the applicableAdmissionDate */
  public Date getApplicableAdmissionDate() {
    return applicableAdmissionDate;
  }

  /**
   * Sets the applicableAdmissionDate.
   *
   * @param aApplicableAdmissionDate the applicableAdmissionDate to set
   */
  public void setApplicableAdmissionDate(Date aApplicableAdmissionDate) {
    applicableAdmissionDate = aApplicableAdmissionDate;
  }

  /** @return the createdDate */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * Sets the createdDate.
   *
   * @param aCreatedDate the createdDate to set
   */
  public void setCreatedDate(Date aCreatedDate) {
    createdDate = aCreatedDate;
  }

  /** @return the alertId */
  public Integer getAlertId() {
    return alertId;
  }

  /**
   * Sets the alertId.
   *
   * @param aAlertId the alertId to set
   */
  public void setAlertId(Integer aAlertId) {
    alertId = aAlertId;
  }

  /** @return the admissionType */
  public Object getAdmissionType() {
    return admissionType;
  }

  /**
   * Sets the admissionType.
   *
   * @param aAdmissionType the admissionType to set
   */
  public void setAdmissionType(Object aAdmissionType) {
    admissionType = aAdmissionType;
  }

  /** @return the contactDetails */
  public String getContactDetails() {
    return contactDetails;
  }

  /**
   * Sets the contactDetails.
   *
   * @param aContactDetails the contactDetails to set
   */
  public void setContactDetails(String aContactDetails) {
    contactDetails = aContactDetails;
  }

  /** @return the suggestString */
  public String getSuggestString() {
    return suggestString;
  }

  /**
   * Sets the suggestString.
   *
   * @param aSuggestString the suggestString to set
   */
  public void setSuggestString(String aSuggestString) {
    suggestString = aSuggestString;
  }

  /** @return the suggestId */
  public String getSuggestId() {
    return suggestId;
  }

  /**
   * Sets the suggestId.
   *
   * @param aSuggestId the suggestId to set
   */
  public void setSuggestId(String aSuggestId) {
    suggestId = aSuggestId;
  }

  /** @return the fullName */
  public String getFullName() {
    return fullName;
  }

  /**
   * Sets the fullName.
   *
   * @param aFullName the fullName to set
   */
  public void setFullName(String aFullName) {
    fullName = aFullName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PersonMinimalDetails) {
      return this.personId == ((PersonMinimalDetails) obj).personId;
    }
    return super.equals(obj);
  }

  @Override
  public int compareTo(PersonMinimalDetails o) {
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
