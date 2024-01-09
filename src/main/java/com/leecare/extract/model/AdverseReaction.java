/*
 * AdverseReaction.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import com.leecare.extract.utils.StringUtils;

import java.io.Serializable;

/**
 * This is used for a AdverseReaction.
 *
 * @author jjoy
 */
public class AdverseReaction implements Serializable {
  public static final int STRING_LENGTH_MAX = 500;

  private Long id;
  private int residentId;
  private String substance;
  private String substanceGroup;
  private String drugAllergy;
  private String drugAllergyReaction;
  private String foodAllergy;
  private String foodAllergyReaction;
  private String otherAllergy;
  private String otherAllergyReaction;
  private String allergyType;
  private String allergyOrSensitivity;
  private String reactionDescription;
  private String comment;
  private String isActive;

  /** @return Id */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID
   *
   * @param id id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /** @return resident ID */
  public int getResidentId() {
    return residentId;
  }

  /**
   * Sets the resident ID
   *
   * @param residentId resident ID
   */
  public void setResidentId(int residentId) {
    this.residentId = residentId;
  }

  /** @return substance */
  public String getSubstance() {
    return substance;
  }

  /**
   * Sets the substance.
   *
   * @param substance substance
   */
  public void setSubstance(String substance) {
    this.substance = StringUtils.getRequiredValue(substance, STRING_LENGTH_MAX);
  }

  /**
   * Gets the substance or agent group.
   *
   * @return the group.
   */
  public String getSubstanceGroup() {
    return substanceGroup;
  }

  /**
   * Sets the substance or agent group.
   *
   * @param aSubstanceGroup the group.
   */
  public void setSubstanceGroup(final String aSubstanceGroup) {
    substanceGroup = StringUtils.getRequiredValue(aSubstanceGroup, STRING_LENGTH_MAX);
  }

  /** @return drug allergy */
  public String getDrugAllergy() {
    return drugAllergy;
  }

  /**
   * Sets the drug allergy
   *
   * @param drugAllergy drugAllergy
   */
  public void setDrugAllergy(String drugAllergy) {
    this.drugAllergy = drugAllergy;
  }

  /** @return drug allergy reaction */
  public String getDrugAllergyReaction() {
    return drugAllergyReaction;
  }

  /**
   * Sets the drug allergy reaction.
   *
   * @param drugAllergyReaction drugAllergyReaction
   */
  public void setDrugAllergyReaction(String drugAllergyReaction) {
    this.drugAllergyReaction = drugAllergyReaction;
  }

  /** @return food allergy */
  public String getFoodAllergy() {
    return foodAllergy;
  }

  /**
   * Sets the food allergy.
   *
   * @param foodAllergy foodAllergy
   */
  public void setFoodAllergy(String foodAllergy) {
    this.foodAllergy = foodAllergy;
  }

  /** @return food allergy reaction. */
  public String getFoodAllergyReaction() {
    return foodAllergyReaction;
  }

  /**
   * Sets the food allergy reaction.
   *
   * @param foodAllergyReaction foodAllergyReaction
   */
  public void setFoodAlleryReaction(String foodAllergyReaction) {
    this.foodAllergyReaction = foodAllergyReaction;
  }

  /** @return other allergy */
  public String getOtherAllergy() {
    return otherAllergy;
  }

  /**
   * Sets other allergy.
   *
   * @param otherAllergy otherAllergy.
   */
  public void setOtherAllergy(String otherAllergy) {
    this.otherAllergy = otherAllergy;
  }

  /** @return other allergy reaction. */
  public String getOtherAllergyReaction() {
    return otherAllergyReaction;
  }

  /**
   * Sets other allergy reaction.
   *
   * @param otherAllergyReaction otherAllergyReaction.
   */
  public void setOtherAllergyReaction(String otherAllergyReaction) {
    this.otherAllergyReaction = otherAllergyReaction;
  }

  /** @return allergy Type. */
  public String getAllergyType() {
    return allergyType;
  }

  /**
   * Sets the Allergy Type.
   *
   * @param allergyType allergyType
   */
  public void setAllergyType(String allergyType) {
    this.allergyType = allergyType;
  }

  /** @return allergy or sensitivity */
  public String getAllergyOrSensitivity() {
    return allergyOrSensitivity;
  }

  /**
   * Sets allergy or sensitivity.
   *
   * @param allergyOrSensitivity allergyOrSensitivity
   */
  public void setAllergyOrSensitivity(String allergyOrSensitivity) {
    this.allergyOrSensitivity = allergyOrSensitivity;
  }

  /** @return the reactionDescription */
  public String getReactionDescription() {
    return reactionDescription;
  }

  /** @param reactionDescription the reactionDescription to set */
  public void setReactionDescription(String reactionDescription) {
    this.reactionDescription = reactionDescription;
  }

  /** @return the comment */
  public String getComment() {
    return comment;
  }

  /** @param comment the comment to set */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /** @return the isActive */
  public String getIsActive() {
    return isActive;
  }

  /**
   * Sets the isActive.
   *
   * @param aIsActive the isActive to set
   */
  public void setIsActive(String aIsActive) {
    isActive = aIsActive;
  }
}
