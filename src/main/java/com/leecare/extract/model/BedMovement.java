/*
 * BedMovement.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

/**
 * This is used for a BedMovement.
 *
 * @author jjoy
 */
public class BedMovement {
  private Integer createdByUserID;
  private String effectiveDateAndTime;
  private String createdTimestamp;
  private Integer id;
  private String bedMovementType;
  private String leaveType;
  /** @return the createdByUserID */
  public Integer getCreatedByUserID() {
    return createdByUserID;
  }
  /**
   * Sets the createdByUserID.
   *
   * @param aCreatedByUserID the createdByUserID to set
   */
  public void setCreatedByUserID(Integer aCreatedByUserID) {
    createdByUserID = aCreatedByUserID;
  }
  /** @return the effectiveDateAndTime */
  public String getEffectiveDateAndTime() {
    return effectiveDateAndTime;
  }
  /**
   * Sets the effectiveDateAndTime.
   *
   * @param aEffectiveDateAndTime the effectiveDateAndTime to set
   */
  public void setEffectiveDateAndTime(String aEffectiveDateAndTime) {
    effectiveDateAndTime = aEffectiveDateAndTime;
  }
  /** @return the createdTimestamp */
  public String getCreatedTimestamp() {
    return createdTimestamp;
  }
  /**
   * Sets the createdTimestamp.
   *
   * @param aCreatedTimestamp the createdTimestamp to set
   */
  public void setCreatedTimestamp(String aCreatedTimestamp) {
    createdTimestamp = aCreatedTimestamp;
  }
  /** @return the id */
  public Integer getId() {
    return id;
  }
  /**
   * Sets the id.
   *
   * @param aId the id to set
   */
  public void setId(Integer aId) {
    id = aId;
  }
  /** @return the bedMovementType */
  public String getBedMovementType() {
    return bedMovementType;
  }
  /**
   * Sets the bedMovementType.
   *
   * @param aBedMovementType the bedMovementType to set
   */
  public void setBedMovementType(String aBedMovementType) {
    bedMovementType = aBedMovementType;
  }
  /** @return the leaveType */
  public String getLeaveType() {
    return leaveType;
  }
  /**
   * Sets the leaveType.
   *
   * @param aLeaveType the leaveType to set
   */
  public void setLeaveType(String aLeaveType) {
    leaveType = aLeaveType;
  }
}
