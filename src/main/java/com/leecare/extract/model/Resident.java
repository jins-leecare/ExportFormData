/*
 * Resident.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

/**
 * This is used for a Resident.
 *
 * @author jjoy
 */
public class Resident {
  private int id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String nationalIDNumber;
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
  /** @return the dateOfBirth */
  public String getDateOfBirth() {
    return dateOfBirth;
  }
  /**
   * Sets the dateOfBirth.
   *
   * @param aDateOfBirth the dateOfBirth to set
   */
  public void setDateOfBirth(String aDateOfBirth) {
    dateOfBirth = aDateOfBirth;
  }
  /** @return the nationalIDNumber */
  public String getNationalIDNumber() {
    return nationalIDNumber;
  }
  /**
   * Sets the nationalIDNumber.
   *
   * @param aNationalIDNumber the nationalIDNumber to set
   */
  public void setNationalIDNumber(String aNationalIDNumber) {
    nationalIDNumber = aNationalIDNumber;
  }
}
